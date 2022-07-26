package com.example.ontapspring0205.repository;

import com.example.ontapspring0205.entity.login.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String username);

    Page<UserEntity> findAll(Pageable pageable);

    UserEntity findByEmail(String email);

    List<UserEntity> findAllByEmail(String email);

    Page<UserEntity> findByUserNameLike(String username, Pageable pageable);
}
