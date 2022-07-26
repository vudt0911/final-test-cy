package com.example.ontapspring0205.model;

import com.example.ontapspring0205.entity.login.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String photo;
    private boolean status;
    private RoleModel roleModel;

    public UserEntity ModelToEntity(UserModel userModel) {
        if (userModel == null) return null;
        return UserEntity.builder()
                .id(userModel.getId())
                .userName(userModel.getUsername())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .phone(userModel.getPhone())
                .address(userModel.getAddress())
                .photo(userModel.getPhoto())
                .dateCreate(new Date())
                .status(true)
                .role(userModel.roleModel != null ? RoleModel.modelToEntity(userModel.roleModel) : null)
                .build();
    }
}
