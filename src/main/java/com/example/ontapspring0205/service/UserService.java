package com.example.ontapspring0205.service;

import com.example.ontapspring0205.dto.RoleDto;
import com.example.ontapspring0205.entity.login.UserEntity;
import com.example.ontapspring0205.model.RoleModel;
import com.example.ontapspring0205.repository.IRoleRepository;
import com.example.ontapspring0205.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    IRoleRepository iRoleRepository;

    public void save(UserEntity userEntity) {
        iUserRepository.save(userEntity);
    }

    public void downloadImage(String url, String fileLocalPath) {
        try (InputStream inputStream = URI.create(url).toURL().openStream()) {
            Files.copy(inputStream, Paths.get(fileLocalPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RoleDto> getAllRole() {
        return iRoleRepository.findAll().stream().map(role -> RoleDto.entityToDto(role)).collect(Collectors.toList());
    }

    public RoleModel getRoleModelById(Long roleId){
        return iRoleRepository.findById(roleId).map(role -> RoleModel.entityToModel(role)).orElse(null);
    }
}

