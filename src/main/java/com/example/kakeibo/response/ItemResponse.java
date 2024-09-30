package com.example.kakeibo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ItemResponse {
    @JsonProperty("expenseId")
    private Integer expenseId;
    @JsonProperty("memo")
    private String memo;
    @JsonProperty("amount")
    private BigDecimal amount;

    public ItemResponse(Integer expenseId, String memo, BigDecimal amount) {
        this.expenseId = expenseId;
        this.memo = memo;
        this.amount = amount;
    }

}