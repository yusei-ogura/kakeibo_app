package com.example.kakeibo.mapper;

import com.example.kakeibo.dto.CategoryDto;
import com.example.kakeibo.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * カテゴリーエンティティをカテゴリーDtoに変換する
     * @param categoryEntity カテゴリーエンティティ
     * @return カテゴリーDto
     */
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "name", target = "name")
    CategoryDto toDto(CategoryEntity categoryEntity);
}
