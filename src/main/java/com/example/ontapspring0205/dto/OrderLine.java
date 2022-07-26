package com.example.ontapspring0205.dto;

import java.io.Serializable;

import com.example.ontapspring0205.entity.home.HomeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderLine implements Serializable {

    private HomeEntity product;
    private int count;
    public void increaseByOne() {
        count += 1;
    }
}
