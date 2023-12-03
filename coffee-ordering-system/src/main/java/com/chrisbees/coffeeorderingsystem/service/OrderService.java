package com.chrisbees.coffeeorderingsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.chrisbees.coffeeorderingsystem.constants.Constants.ORDER_UPDATES;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public boolean sendOrderUpdates(String updates){

        kafkaTemplate.send(ORDER_UPDATES, updates);

        return true;
    }

}
