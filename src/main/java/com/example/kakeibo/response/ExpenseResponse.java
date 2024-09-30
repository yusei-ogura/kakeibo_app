package com.example.kakeibo.response;

import com.example.kakeibo.response.CategoryResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支出一覧Form
 */
public class ExpenseResponse {
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("categories")
    private Map<String, CategoryResponse> categories;

    public ExpenseResponse(BigDecimal totalAmount, Map<String, CategoryResponse> categories) {
        this.totalAmount = totalAmount;
        this.categories = categories;
    }

}