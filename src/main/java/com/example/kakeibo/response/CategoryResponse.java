package com.example.kakeibo.response;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

/**
 * カテゴリー一覧Response
 */
@Getter
public class CategoryResponse {
    private Integer totalAmount;
    private List<ItemResponse> items;

    public CategoryResponse(Integer totalAmount, List<ItemResponse> items) {
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<ItemResponse> getItems() {
        return items;
    }

}