package com.example.kakeibo.dto;

import com.example.kakeibo.request.ExpenseEditRequest;
import com.example.kakeibo.request.ExpenseRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseCommandDto {

    private Integer amount;
    private Integer categoryId;
    private String memo;
    private LocalDate paymentDate;

    public static ExpenseCommandDto from(ExpenseRegisterRequest request) {
        return new ExpenseCommandDto(
                request.getAmount(),
                request.getCategoryId(),
                request.getMemo(),
                request.getPaymentDate()
        );
    }

    public static ExpenseCommandDto from(ExpenseEditRequest request) {
        return new ExpenseCommandDto(
                request.getAmount(),
                request.getCategoryId(),
                request.getMemo(),
                request.getPaymentDate()
        );
    }
}
