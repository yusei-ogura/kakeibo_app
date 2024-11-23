package com.example.kakeibo.service;

import com.example.kakeibo.dao.CategoryDao;
import com.example.kakeibo.dto.CategoryCommandDto;
import com.example.kakeibo.dto.CategoryDto;
import com.example.kakeibo.entity.CategoryEntity;
import com.example.kakeibo.exception.CategoryDeletionException;
import com.example.kakeibo.exception.CategoryNotFoundException;
import com.example.kakeibo.exception.ExpenseRegistrationException;
import com.example.kakeibo.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    /**
     * カテゴリーリストを取得する
     * @return カテゴリーリストのDTO
     */
    public List<CategoryDto> getAll() {
        List<CategoryEntity> categoryList = categoryDao.selectAll();
        return categoryList.stream()
                .filter(category -> !category.isDeleteFlg())
                .map(categoryMapper::toDto)
                .toList();
    }

    /**
     * カテゴリーを登録する
     * commandDto カテゴリー情報Dto
     */
    public void register(CategoryCommandDto commandDto) {
        try {
            CategoryEntity entity = new CategoryEntity();
            entity.setName(commandDto.getName());
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            entity.setDeleteFlg(false);

            categoryDao.insert(entity);
        } catch (DataAccessException e) {
            throw new ExpenseRegistrationException("カテゴリーの登録に失敗しました");
        }
    }

    /**
     * カテゴリーを削除する
     * @param categoryId カテゴリーID
     */
    public void delete(Integer categoryId) {
            CategoryEntity entity = findCategoryById(categoryId);
            entity.setDeleteFlg(true);
            int result = categoryDao.updateDeleteFlg(entity);

            if (result != 1) {
            throw new CategoryDeletionException("カテゴリーの削除に失敗しました");
        }
    }

    /**
     * カテゴリーIDからカテゴリーを取得する
     * @param categoryId カテゴリーID
     */
    public CategoryEntity findCategoryById(Integer categoryId) {
        return categoryDao.selectById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("選択されたカテゴリが存在しません"));
    }

}
