package com.example.kakeibo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * カテゴリー一覧Form
 */
public class CategoryResponse {
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("items")
    private List<ItemResponse> items;

    public CategoryResponse(BigDecimal totalAmount, List<ItemResponse> items) {
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<ItemResponse> getItems() {
        return items;
    }

}