package com.example.kakeibo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseEditRequest {

    /** 金額 */
    @NotBlank(message = "金額は必須です")
    private Integer amount;

    /** カテゴリーID */
    @NotBlank(message = "カテゴリーIDは必須です")
    private Integer categoryId;

    /** メモ */
    @Size(max = 150, message = "メモは150字以内で入力してください")

    private String memo;

    /** 支払日 */
    @NotBlank(message = "支払日は必須です")
    private LocalDate paymentDate;

}
