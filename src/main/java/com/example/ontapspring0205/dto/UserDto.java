package com.example.ontapspring0205.dto;

import com.example.ontapspring0205.entity.login.UserEntity;
import com.example.ontapspring0205.model.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String photo;
    private Date dateCreate;
    private boolean status;
    private RoleDto roleDto;

    public UserDto entityToDto(UserEntity userEntity){
        if (userEntity == null) return null;
        return UserDto.builder().id(userEntity.getId())
                .username(userEntity.getUserName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phone(userEntity.getPhone())
                .address(userEntity.getAddress())
                .photo(userEntity.getPhoto())
                .dateCreate(userEntity.getDateCreate())
                .status(userEntity.isStatus())
                .roleDto(userEntity.getRole() != null ? RoleDto.entityToDto(userEntity.getRole()) : null)
                .build();
    }
}
