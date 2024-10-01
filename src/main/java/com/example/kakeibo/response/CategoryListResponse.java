package com.example.kakeibo.response;

import com.example.kakeibo.dto.CategoryDto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CategoryListResponse implements ApiResponse {
    private List<CategoryDto> categories;

    public List<CategoryDto> getCategories() {
        return categories;
    }
}
