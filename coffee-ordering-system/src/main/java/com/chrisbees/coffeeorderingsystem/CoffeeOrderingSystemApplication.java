package com.chrisbees.coffeeorderingsystem;

import com.chrisbees.coffeeorderingsystem.verticles.OrderUpdatesVerticle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class CoffeeOrderingSystemApplication {


	public static void main(String[] args) {
		new SpringApplicationBuilder(CoffeeOrderingSystemApplication.class)
		.web(WebApplicationType.NONE)
		.run(args);
	}



//	ObjectMapper om = new ObjectMapper();
//  objectMapper.registerModule(new JavaTimeModule());




}
