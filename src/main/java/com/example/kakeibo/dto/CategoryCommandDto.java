package com.example.kakeibo.dto;

import com.example.kakeibo.request.CategoryRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryCommandDto {

    private String name;

    /**
     * リクエストからDTOを生成
     */
    public static CategoryCommandDto from(CategoryRegisterRequest request) {
        return new CategoryCommandDto(request.getName());
    }
}
