package com.example.kakeibo.response.successDto;

import com.example.kakeibo.response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExpenseSuccessDto implements ApiResponse {
    @JsonProperty("message")
    private String message;

}
