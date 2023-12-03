package com.chrisbees.coffeeorderingsystem.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.time.Duration;

@Data
public class OrderResponse {

    private String message;
    private Long duration;


}
