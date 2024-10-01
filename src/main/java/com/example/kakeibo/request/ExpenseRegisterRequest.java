package com.example.kakeibo.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRegisterRequest {

    /** 金額 */
    @NotNull(message = "金額は必須です")
    private Integer amount;

    /** カテゴリーID */
    @NotNull(message = "カテゴリーIDは必須です")
    private Integer categoryId;

    /** メモ */
    @Size(max = 150, message = "メモは150字以内で入力してください")
    private String memo;

    /** 支払日 */
    @NotNull(message = "支払日は必須です")
    private LocalDate paymentDate;

}