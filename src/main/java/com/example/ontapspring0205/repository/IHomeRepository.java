package com.example.ontapspring0205.repository;

import com.example.ontapspring0205.entity.home.HomeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHomeRepository extends JpaRepository<HomeEntity, Long> {
    Page<HomeEntity> findAll(Pageable pageable);
    Page<HomeEntity> findByNameLike(String name, Pageable pageable);
    Page<HomeEntity> findByCategoryId(Long categoryId, Pageable pageable);
    List<HomeEntity> findByCategoryId(Long categoryId);
}
