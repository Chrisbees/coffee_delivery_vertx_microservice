package com.chrisbees.coffeeconsumer;

import com.chrisbees.coffeeconsumer.service.CustomerService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MainVerticle extends AbstractVerticle {

    private final CustomerService customerService;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        Router router = Router.router(vertx);

//
//        ConfigStoreOptions store = new ConfigStoreOptions()
//                .setType("spring-config-server")
//                .setConfig(new JsonObject().put("url", "http://localhost:8888"));
//
//        ConfigRetriever retriever = ConfigRetriever.create(vertx,
//                new ConfigRetrieverOptions().addStore(store));

        router.route().handler(BodyHandler.create());
        router.post("/create-account").handler(customerService.createCustomerAccount());
        router.get("/get-all").handler(customerService.getAllCustomers());
        router.get("/get-customer/:userId").handler(customerService.getCustomerById());
        router.delete("/delete-customer/:userId").handler(customerService.deleteCustomerById());
        router.post("/place-order/:userId").handler(customerService.customerOrder());

        vertx.createHttpServer().requestHandler(router)
                .listen(9001)
                .onSuccess(success -> {
                    log.info("Server started on port {}", success.actualPort());
                    startPromise.complete();
                }).onFailure(fail -> {
                    startPromise.fail(fail.getCause());
                        }
                );
    }
}
