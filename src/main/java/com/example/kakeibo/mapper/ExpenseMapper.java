package com.example.kakeibo.mapper;

import com.example.kakeibo.dto.ExpenseDto;
import com.example.kakeibo.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    /**
     * 支出エンティティを支出Dtoに変換する
     * @param expenseEntity 支出エンティティ
     * @return 支出Dto
     */
    @Mapping(source = "expenseId", target = "expenseId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "memo", target = "memo")
    @Mapping(source = "paymentDate", target = "paymentDate")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    ExpenseDto toDto(ExpenseEntity expenseEntity);

}
