package com.example.kakeibo.service;

import com.example.kakeibo.dao.ExpenseDao;
import com.example.kakeibo.dto.ExpenseDto;
import com.example.kakeibo.entity.ExpenseEntity;
import com.example.kakeibo.exception.ExpenseDeletionException;
import com.example.kakeibo.exception.ExpenseEditException;
import com.example.kakeibo.exception.ExpenseRegistrationException;
import com.example.kakeibo.mapper.ExpenseMapper;
import com.example.kakeibo.request.ExpenseEditRequest;
import com.example.kakeibo.request.ExpenseRegisterRequest;
import com.example.kakeibo.response.CategoryExpenseElem;
import com.example.kakeibo.response.ExpenseDetail;
import com.example.kakeibo.response.MonthlyExpenseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final CategoryService categoryService;
    private final ExpenseDao expenseDao;
    private final ExpenseMapper expenseMapper;

    /**
     * 指定した年月の支出リストを取得する
     * @param targetMonth 対象年月
     * @return 支出リストのDTO
     */
    public List<ExpenseDto> findExpensesByMonth(YearMonth targetMonth) {
        int year = targetMonth.getYear();
        int month = targetMonth.getMonthValue();

        List<ExpenseEntity> expenseList = expenseDao.selectByYearAndMonth(year, month);
        return expenseList.stream()
                .map(expenseMapper::toDto)
                .toList();
    }

    /**
     * 支出を登録する
     * @param request 支出登録リクエスト
     */
    public void register(ExpenseRegisterRequest request) {

        try {
            ExpenseEntity entity = new ExpenseEntity();
            entity.setAmount(request.getAmount());
            entity.setCategoryId(request.getCategoryId());
            entity.setCategoryName(categoryService.findCategoryById(request.getCategoryId()).getName());
            entity.setMemo(request.getMemo());
            entity.setPaymentDate(request.getPaymentDate());
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());

            expenseDao.insert(entity);
        } catch (DataAccessException e) {
            throw new ExpenseRegistrationException("支出の登録に失敗しました");
        }
    }

    /**
     * 支出を編集する
     * @param expenseId 支出ID
     * @param request 支出編集リクエスト
     */
    public void edit(Integer expenseId, ExpenseEditRequest request) {
        categoryService.findCategoryById(request.getCategoryId());

        ExpenseEntity existingEntity = expenseDao.selectById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("支出が見つかりません"));
        existingEntity.setAmount(request.getAmount());
        existingEntity.setCategoryId(request.getCategoryId());
        existingEntity.setMemo(request.getMemo());
        existingEntity.setPaymentDate(request.getPaymentDate());
        existingEntity.setUpdatedAt(LocalDateTime.now());

        int result = expenseDao.update(existingEntity);
        if (result != 1) {
            throw new ExpenseEditException("支出の編集に失敗しました");
        }
    }

    /**
     * 支出を削除する
     * @param expenseId 支出ID
     */
    public void delete(Integer expenseId) {
        ExpenseEntity entity = new ExpenseEntity();
        entity.setExpenseId(expenseId);
        int result = expenseDao.delete(entity);
        if (result != 1) {
            throw new ExpenseDeletionException("支出の削除に失敗しました");
        }
    }

    /**
     * カテゴリー毎の支出を生成する
     * @param expenseListDto 支出Dtoリスト
     * @return カテゴリー毎の支出リスト
     */
    public Map<String, CategoryExpenseElem> getCategoryExpenses(List<ExpenseDto> expenseListDto) {
        Map<String, CategoryExpenseElem> categoryMap = new HashMap<>();

        for (ExpenseDto dto : expenseListDto) {
            String categoryName = dto.getCategoryName();
            Integer amount = dto.getAmount();

            CategoryExpenseElem categoryElem = categoryMap.get(categoryName);
            if (categoryElem == null) {
                categoryElem = new CategoryExpenseElem(categoryName, 0, new ArrayList<>());
                categoryMap.put(categoryName, categoryElem);
            }
            categoryElem.setTotalAmount(categoryElem.getTotalAmount() + amount);
            categoryElem.getExpenseDetails().add(new ExpenseDetail(dto.getExpenseId(), dto.getMemo(), amount));
        }
        return categoryMap;
    }

    /**
     * 支出Dtoを基に支出一覧Responseを生成する
     * @param expenseListDto 支出Dtoリスト
     * @return 月別支出Response
     */
    public MonthlyExpenseResponse createMonthlyExpenseResponse(List<ExpenseDto> expenseListDto) {
        Map<String, CategoryExpenseElem> categoryExpensesMap = getCategoryExpenses(expenseListDto);

        Integer totalAmount = categoryExpensesMap.values().stream()
                .mapToInt(CategoryExpenseElem::getTotalAmount)
                .sum();

        List<CategoryExpenseElem> categoryExpenses = new ArrayList<>(categoryExpensesMap.values());

        return new MonthlyExpenseResponse(totalAmount, categoryExpenses);
    }


}
