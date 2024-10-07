package com.example.kakeibo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseDetail {
    private Integer expenseId;
    private String memo;
    private Integer amount;

}