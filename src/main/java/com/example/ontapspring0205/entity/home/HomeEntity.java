package com.example.ontapspring0205.entity.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "home")
public class HomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "price")
    private Long price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "sale_quantity")
    private Integer saleQuantity;
    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
