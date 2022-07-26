package com.example.ontapspring0205.dto;

import com.example.ontapspring0205.entity.login.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private Long id;
    private String name;

    public static RoleDto entityToDto(RoleEntity role) {
        if (role == null) return null;
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
