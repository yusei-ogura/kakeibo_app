package com.example.kakeibo.mapper;

import com.example.kakeibo.dto.CategoryDto;
import com.example.kakeibo.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * カテゴリーエンティティをカテゴリーDtoに変換する
     * @param categoryEntity カテゴリーエンティティ
     * @return カテゴリーDto
     */
    CategoryDto toDto(CategoryEntity categoryEntity);
}
