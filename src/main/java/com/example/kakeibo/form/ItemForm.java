package com.example.kakeibo.form;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ItemForm {
    @JsonProperty("expenseId")
    private Integer expenseId;
    @JsonProperty("memo")
    private String memo;
    @JsonProperty("amount")
    private BigDecimal amount;

    public ItemForm(Integer expenseId, String memo, BigDecimal amount) {
        this.expenseId = expenseId;
        this.memo = memo;
        this.amount = amount;
    }

}