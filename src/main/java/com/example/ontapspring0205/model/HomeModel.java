package com.example.ontapspring0205.model;

import com.example.ontapspring0205.entity.home.HomeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeModel {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Long price;
    private Integer quantity;
    private Integer saleQuantity;
    private Boolean status;

    private CategoryModel categoryModel;

    public static HomeEntity modelToEntity(HomeModel homeModel){
        if(homeModel == null) return null;
        return HomeEntity.builder()
                .id(homeModel.getId())
                .name(homeModel.getName())
                .description(homeModel.getDescription())
                .image(homeModel.getImage())
                .price(homeModel.getPrice())
                .quantity(homeModel.getQuantity())
                .saleQuantity(homeModel.getSaleQuantity())
                .status(homeModel.status)
                .category(homeModel.categoryModel.modelToEntity(homeModel.getCategoryModel()))
                .build();
    }

    public static HomeModel entityToModel(HomeEntity homeEntity) {
        return HomeModel.builder()
                .id(homeEntity.getId())
                .name(homeEntity.getName())
                .description(homeEntity.getDescription())
                .image(homeEntity.getImage())
                .price(homeEntity.getPrice())
                .categoryModel(homeEntity.getCategory() == null ? null : CategoryModel.entityToModel(homeEntity.getCategory()))
                .build();
    }
}
