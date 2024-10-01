package com.example.kakeibo.response.errorDto;

import com.example.kakeibo.response.ApiResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategoryErrorDto implements ApiResponse {
    private String message;
}
