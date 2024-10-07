package com.example.kakeibo.response;

import lombok.Getter;

import java.util.List;

/**
 * 支出一覧Response
 */
@Getter
public class MonthlyExpenseResponse implements ApiResponse {
    private Integer totalAmount;
    private List<CategoryExpenseElem> categoryExpenses;

    public MonthlyExpenseResponse(Integer totalAmount, List<CategoryExpenseElem> categoryExpenses) {
        this.totalAmount = totalAmount;
        this.categoryExpenses = categoryExpenses;
    }

}