package com.example.kakeibo.service;

import com.example.kakeibo.dao.ExpenseDao;
import com.example.kakeibo.dto.ExpenseDto;
import com.example.kakeibo.entity.ExpenseEntity;
import com.example.kakeibo.exception.ExpenseDeletionException;
import com.example.kakeibo.exception.ExpenseEditException;
import com.example.kakeibo.exception.ExpenseRegistrationException;
import com.example.kakeibo.form.CategoryForm;
import com.example.kakeibo.form.ExpenseForm;
import com.example.kakeibo.form.ItemForm;
import com.example.kakeibo.mapper.ExpenseMapper;
import com.example.kakeibo.request.ExpenseEditRequest;
import com.example.kakeibo.request.ExpenseRegisterRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<ExpenseDto> findExpenseListByMonth(YearMonth targetMonth) {
        int year = targetMonth.getYear();
        int month = targetMonth.getMonthValue();

        List<ExpenseEntity> expenseList = expenseDao.selectByYearAndMonth(year, month);
        return expenseList.stream()
                .map(expense -> {
                    ExpenseDto dto = expenseMapper.toDto(expense);
                    String categoryName = categoryService.getCategoryName(expense.getCategoryId());
                    dto.setCategoryName(categoryName);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 支出を登録する
     * @param request 支出登録リクエスト
     */
    public void registerExpense(ExpenseRegisterRequest request) {
        categoryService.findCategoryById(request.getCategoryId());

        ExpenseEntity entity = new ExpenseEntity();
        entity.setAmount(request.getAmount());
        entity.setCategoryId(request.getCategoryId());
        entity.setMemo(request.getMemo());
        entity.setPaymentDate(request.getPaymentDate());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        int result = expenseDao.insert(entity);
        if (result <= 0) {
            throw new ExpenseRegistrationException("支出の登録に失敗しました");
        }
    }

    /**
     * 支出を編集する
     * @param expenseId 支出ID
     * @param request 支出編集リクエスト
     */
    public void editExpense(Integer expenseId, ExpenseEditRequest request) {
        categoryService.findCategoryById(request.getCategoryId());

        ExpenseEntity existingEntity = expenseDao.selectById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("支出が見つかりません"));
        existingEntity.setAmount(request.getAmount());
        existingEntity.setCategoryId(request.getCategoryId());
        existingEntity.setMemo(request.getMemo());
        existingEntity.setPaymentDate(request.getPaymentDate());
        existingEntity.setUpdatedAt(LocalDateTime.now());

        int result = expenseDao.update(existingEntity);
        if (result <= 0) {
            throw new ExpenseEditException("支出の編集に失敗しました");
        }
    }

    /**
     * 支出を削除する
     * @param expenseId 支出ID
     */
    public void deleteExpense(Integer expenseId) {
        ExpenseEntity entity = new ExpenseEntity();
        entity.setExpenseId(expenseId);
        int result = expenseDao.delete(entity);
        if (result <= 0) {
            throw new ExpenseDeletionException("支出の削除に失敗しました");
        }
    }

    /**
     * 支出Dtoを基に支出一覧Formを生成する
     * @param expenseListDto 支出Dto
     * @return 支出一覧Form
     */
    public ExpenseForm createExpenseForm(List<ExpenseDto> expenseListDto) {
        Map<String, CategoryForm> categoryMap = new HashMap<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ExpenseDto dto : expenseListDto) {
            String categoryName = dto.getCategoryName();
            CategoryForm categoryForm = categoryMap.computeIfAbsent(categoryName, k -> new CategoryForm(BigDecimal.ZERO, new ArrayList<>()));
            BigDecimal amount = BigDecimal.valueOf(dto.getAmount());
            categoryForm.setTotalAmount(categoryForm.getTotalAmount().add(amount));
            categoryForm.getItems().add(new ItemForm(dto.getExpenseId(), dto.getMemo(), amount));
            totalAmount = totalAmount.add(amount);
        }
        return new ExpenseForm(totalAmount, categoryMap);
    }

}