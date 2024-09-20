package com.example.kakeibo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRegisterRequest {

    /** カテゴリー名 */
    @NotBlank(message = "カテゴリー名は必須です")
    private String name;

}
