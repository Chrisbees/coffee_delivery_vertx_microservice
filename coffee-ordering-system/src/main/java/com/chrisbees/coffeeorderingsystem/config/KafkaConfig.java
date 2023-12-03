package com.chrisbees.coffeeorderingsystem.config;

import com.chrisbees.coffeeorderingsystem.constants.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.chrisbees.coffeeorderingsystem.constants.Constants.ORDER_UPDATES;

@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic kafkaTopic(){
        return TopicBuilder
                .name(ORDER_UPDATES)
                .build();
    }
}
