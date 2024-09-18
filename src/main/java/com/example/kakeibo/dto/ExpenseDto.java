package com.example.kakeibo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExpenseDto {

    /** 支出ID */
    private Integer expenseId;

    /** 金額 */
    private Integer amount;

    /** カテゴリID */
    private Integer categoryId;

    /** メモ */
    private String memo;

    /** 支払日 */
    private LocalDate paymentDate;

    /** 作成日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;
}
