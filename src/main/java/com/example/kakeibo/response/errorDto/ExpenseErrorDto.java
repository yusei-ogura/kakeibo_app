package com.example.kakeibo.response.errorDto;

import com.example.kakeibo.response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExpenseErrorDto implements ApiResponse {
    @JsonProperty("message")
    private String message;

}
