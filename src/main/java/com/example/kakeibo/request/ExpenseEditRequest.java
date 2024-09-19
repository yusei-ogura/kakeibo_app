package com.example.kakeibo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseEditRequest {

    /** 金額 */
    @NotNull(message = "金額は必須です")
    private Integer amount;

    /** カテゴリーID */
    @NotNull(message = "カテゴリーIDは必須です")
    private Integer categoryId;

    /** メモ */
    private String memo;

    /** 支払日 */
    @NotNull(message = "支払日は必須です")
    private LocalDate paymentDate;
}
