package com.example.kakeibo.service;

import com.example.kakeibo.dao.ExpenseDao;
import com.example.kakeibo.dto.ExpenseDto;
import com.example.kakeibo.entity.ExpenseEntity;
import com.example.kakeibo.mapper.ExpenseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseDao expenseDao;

    private final ExpenseMapper expenseMapper;

    /**
     * 指定した年月の支出リストを取得する
     * @param targetMonth 対象年月
     * @return 支出リストのDTO
     */
    public List<ExpenseDto>  findExpenseListByMonth(YearMonth targetMonth) {
        int year = targetMonth.getYear();
        int month = targetMonth.getMonthValue();

        // 対象の年・月に基づいた支出データを取得
        List<ExpenseEntity> expenseList = expenseDao.selectByYearAndMonth(year,month);
        return expenseList.stream().map(expenseMapper::toDto).collect(Collectors.toList());
    }

}

