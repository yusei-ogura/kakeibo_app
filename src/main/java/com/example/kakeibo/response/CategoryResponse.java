package com.example.kakeibo.response;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

/**
 * カテゴリー一覧Response
 */
@Getter
public class CategoryResponse {
    private BigDecimal totalAmount;
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