package com.chrisbees.coffeeconsumer.config;

import com.chrisbees.coffeeconsumer.MainVerticle;
import io.vertx.core.Vertx;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class VertxConfig {

    private final MainVerticle mainVerticle;

    @EventListener(ApplicationReadyEvent.class)
    public void configVertx(){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(mainVerticle);
    }

}

