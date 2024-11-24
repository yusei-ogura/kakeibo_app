package com.example.kakeibo.service;

import com.example.kakeibo.dao.ExpenseDao;
import com.example.kakeibo.dto.ExpenseCommandDto;
import com.example.kakeibo.dto.ExpenseDto;
import com.example.kakeibo.entity.ExpenseEntity;
import com.example.kakeibo.entity.CategoryEntity;
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

import java.time.LocalDate;
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
    public List<ExpenseDto> findExpensesByMonth(YearMonth targetMonth) {
        LocalDate fromDate = targetMonth.atDay(1);
        LocalDate toDate = fromDate.plusMonths(1);
        List<ExpenseEntity> expenseList = expenseDao.selectByDateRange(fromDate, toDate);

        List<ExpenseDto> expenseDto = new ArrayList<>();

        Map<Integer, String> categoryMap = categoryService.findCategoryByIds(categoryIds).stream()
                .collect(Collectors.toMap(CategoryEntity::getCategoryId, CategoryEntity::getName));

        // DTOの生成
        List<ExpenseDto> expenseDtoList = new ArrayList<>();
        for (ExpenseEntity expense : expenseList) {
            ExpenseDto dto = new ExpenseDto();
            dto.setExpenseId(expense.getExpenseId());
            dto.setAmount(expense.getAmount());
            dto.setCategoryId(expense.getCategoryId());
            dto.setCategoryName(categoryMap.get(expense.getCategoryId()));
            dto.setMemo(expense.getMemo());
            dto.setPaymentDate(expense.getPaymentDate());
            dto.setCreatedAt(expense.getCreatedAt());
            dto.setUpdatedAt(expense.getUpdatedAt());

            expenseDtoList.add(dto);
        }
        return expenseDtoList;
    }

    /**
     * 支出を登録する
     * commandDto 支出情報Dto
     */
    public void register(ExpenseCommandDto commandDto) {

        try {
            ExpenseEntity entity = new ExpenseEntity();
            entity.setAmount(commandDto.getAmount());
            entity.setCategoryId(commandDto.getCategoryId());
            entity.setMemo(commandDto.getMemo());
            entity.setPaymentDate(commandDto.getPaymentDate());
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
     * commandDto 支出情報Dto
     */
    public void edit(Integer expenseId, ExpenseCommandDto commandDto) {
        categoryService.findCategoryById(commandDto.getCategoryId());

        ExpenseEntity existingEntity = expenseDao.selectById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("支出が見つかりません"));
        existingEntity.setAmount(commandDto.getAmount());
        existingEntity.setCategoryId(commandDto.getCategoryId());
        existingEntity.setMemo(commandDto.getMemo());
        existingEntity.setPaymentDate(commandDto.getPaymentDate());
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
     * カテゴリー毎の支出の総額を計算する
     * @param categoryExpensesMap カテゴリー別の支出情報
     * @return カテゴリー全体の総額
     */
    public Integer calculateTotalAmount(Map<String, CategoryExpenseElem> categoryExpensesMap) {
        return categoryExpensesMap.values().stream()
                .mapToInt(CategoryExpenseElem::getTotalAmount)
                .sum();
    }

}
