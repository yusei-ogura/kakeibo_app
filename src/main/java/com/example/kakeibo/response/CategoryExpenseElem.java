package com.example.kakeibo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CategoryExpenseElem {
    private String name;
    private Integer totalAmount;
    private List<ExpenseDetail> expenseDetails;

}