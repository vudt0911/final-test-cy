package com.example.ontapspring0205.dto;

import com.example.ontapspring0205.entity.home.HomeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeDto {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Long price;
    private Integer quantityDto;
    private CategoryDto categoryDto;

    public static HomeDto entityToDto(HomeEntity homeEntity) {
        return HomeDto.builder()
                .id(homeEntity.getId())
                .name(homeEntity.getName())
                .description(homeEntity.getDescription())
                .image(homeEntity.getImage())
                .price(homeEntity.getPrice())
                .quantityDto(homeEntity.getQuantity())
                .categoryDto(homeEntity.getCategory() == null ? null : CategoryDto.entityToDto(homeEntity.getCategory()))
                .build();
    }
}
