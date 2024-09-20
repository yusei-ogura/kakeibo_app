package com.example.kakeibo.form;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支出一覧Form
 */
public class ExpenseForm {
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("categories")
    private Map<String, CategoryForm> categories;

    public ExpenseForm(BigDecimal totalAmount, Map<String, CategoryForm> categories) {
        this.totalAmount = totalAmount;
        this.categories = categories;
    }

}