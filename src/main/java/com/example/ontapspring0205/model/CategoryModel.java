package com.example.ontapspring0205.model;

import com.example.ontapspring0205.entity.home.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryModel {
    private Long id;
    private String name;

    public static CategoryEntity modelToEntity(CategoryModel categoryModel) {
        if(categoryModel == null) return null;
        return CategoryEntity.builder()
                .id(categoryModel.getId())
                .name(categoryModel.getName())
                .build();
    }

    public static CategoryModel entityToModel(CategoryEntity categoryEntity) {
        return CategoryModel.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .build();
    }
}
