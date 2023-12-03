package com.chrisbees.coffeeconsumer.consume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumeUpdates {

    @KafkaListener(topics = "order-updates", groupId = "user-id")
    private void receiveCoffeeUpdates(String msg){
        log.info(msg);
    }
}
