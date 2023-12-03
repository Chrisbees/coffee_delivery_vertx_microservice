package com.chrisbees.coffeeconsumer.service;

import com.chrisbees.coffeeconsumer.model.MakeOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static com.chrisbees.coffeeconsumer.constant.Constants.CUSTOMER_ORDER;

@Service
@RequiredArgsConstructor
public class KafkaCustomerService {


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public boolean customerCoffeeRequest(MakeOrder request){

        kafkaTemplate.send(CUSTOMER_ORDER, request);
        return true;
    }
}
