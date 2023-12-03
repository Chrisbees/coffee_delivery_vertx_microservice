package com.chrisbees.coffeeorderingsystem.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class OrderRequest {

    private Integer id;
    private String customerName;
    private String order;

}
