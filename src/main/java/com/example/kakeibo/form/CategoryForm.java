package com.example.kakeibo.form;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * カテゴリー一覧Form
 */
public class CategoryForm {
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("items")
    private List<ItemForm> items;

    public CategoryForm(BigDecimal totalAmount, List<ItemForm> items) {
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<ItemForm> getItems() {
        return items;
    }

}