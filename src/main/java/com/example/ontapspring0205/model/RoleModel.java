package com.example.ontapspring0205.model;

import com.example.ontapspring0205.entity.login.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleModel {
    private Long id;
    private String name;

    public static RoleEntity modelToEntity(RoleModel roleModel) {
        if (roleModel == null) return null;
        return RoleEntity.builder()
                .id(roleModel.getId())
                .name(roleModel.getName())
                .build();
    }

    public static RoleModel entityToModel(RoleEntity roleEntity) {
        if (roleEntity == null) return null;
        return RoleModel.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();
    }
}
