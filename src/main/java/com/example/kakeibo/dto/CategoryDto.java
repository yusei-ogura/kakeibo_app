package com.example.kakeibo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * カテゴリーDTO
 */
@Getter
@Setter
public class CategoryDto {

    /** カテゴリーID */
    private Integer categoryId;

    /** カテゴリー名 */
    private String name;

}
