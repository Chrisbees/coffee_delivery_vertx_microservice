package com.chrisbees.coffeeorderingsystem.config;


import com.chrisbees.coffeeorderingsystem.verticles.OrderUpdatesVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;


@Configuration
@RequiredArgsConstructor
public class VertxVerticleConfig {


    private final OrderUpdatesVerticle orderUpdatesVerticle;

    @Bean
    public Vertx vertx(){
        return Vertx.vertx();
    }

    @EventListener(ApplicationReadyEvent.class)
    //    @PostConstruct
    public void deployVert(){
        Vertx vertx = Vertx.vertx(new VertxOptions().setEventLoopPoolSize(5));
        vertx.deployVerticle(orderUpdatesVerticle);
    }
}
