package com.example.kakeibo.response;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ItemResponse {
    private Integer expenseId;
    private String memo;
    private Integer amount;

    public ItemResponse(Integer expenseId, String memo, Integer amount) {
        this.expenseId = expenseId;
        this.memo = memo;
        this.amount = amount;
    }

}