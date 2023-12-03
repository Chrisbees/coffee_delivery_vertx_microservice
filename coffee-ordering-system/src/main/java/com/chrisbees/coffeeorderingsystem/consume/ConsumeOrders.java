package com.chrisbees.coffeeorderingsystem.consume;

import com.chrisbees.coffeeorderingsystem.model.OrderRequest;
import com.chrisbees.coffeeorderingsystem.verticles.HandleOrders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsumeOrders {


    private final HandleOrders handleOrders;

    @KafkaListener(topics = "customer-order", groupId = "user-id")
    public void receiveOrderUpdates(String msg) throws JsonProcessingException {
        OrderRequest orderRequest = new ObjectMapper().readValue(msg, OrderRequest.class);
        log.info(orderRequest.toString());

        handleOrders.processOrders(orderRequest);
    }

}
