package com.chrisbees.coffeeorderingsystem.verticles;


import com.chrisbees.coffeeorderingsystem.model.OrderRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderUpdatesVerticle extends AbstractVerticle {

    private final Logger LOG = LoggerFactory.getLogger(OrderUpdatesVerticle.class.getName());
    private final HandleOrders orders;
    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        WebClient webClient = WebClient.create(vertx);
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.put("/order-updates/:userId").handler(orders.handlePutRequest());
       // router.post("/add-order").handler(orders.handleAddOrders());
        router.get("/get-orders").handler(orders.handleGetOrders());
        router.get("/coffee-shop/allCustomers").handler(context1 -> {
                webClient.get(9001, "localhost", "/get-all")
                        .send().onSuccess(success -> {
                            log.info("All customers successfully retrieved");
                            context1.response()
                                    .putHeader("content-type", "application/json")
                                    .end(success.body());
                        }).onFailure(fail -> {
                            log.info("failed to retrieve customers");
                            context1.response().end(fail.getCause().toString());
                        });
        });


        vertx.createHttpServer().requestHandler(router)
                .listen(9000)
                .onSuccess(success -> {
                    LOG.info("Server started on port {}", success.actualPort());
                    startPromise.complete();
                }).onFailure(startPromise::fail);

    }
}
