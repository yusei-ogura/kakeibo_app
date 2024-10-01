package com.example.kakeibo.mapper;

import com.example.kakeibo.dto.ExpenseDto;
import com.example.kakeibo.entity.ExpenseEntity;
import com.example.kakeibo.service.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class ExpenseMapper {

    @Autowired
    protected CategoryService categoryService;

    public abstract ExpenseDto toDto(ExpenseEntity expenseEntity);
}
