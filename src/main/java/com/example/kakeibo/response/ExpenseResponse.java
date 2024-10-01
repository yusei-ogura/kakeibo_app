package com.example.kakeibo.response;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支出一覧Response
 */
@Getter
public class ExpenseResponse {
    private Integer totalAmount;
    private Map<String, CategoryResponse> categories;

    public ExpenseResponse(Integer totalAmount, Map<String, CategoryResponse> categories) {
        this.totalAmount = totalAmount;
        this.categories = categories;
    }

}