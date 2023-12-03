package com.chrisbees.coffeeconsumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.chrisbees.coffeeconsumer.constant.Constants.CUSTOMER_ORDER;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic customerTopic(){
        return TopicBuilder
                .name(CUSTOMER_ORDER)
                .build();
    }


}
