package com.chrisbees.coffeeconsumer.service;

import com.chrisbees.coffeeconsumer.model.Customer;
import com.chrisbees.coffeeconsumer.model.MakeOrder;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final Map<Integer, Customer> customerList = new HashMap<>();

    private final KafkaCustomerService sendRequests;

    public Handler<RoutingContext> createCustomerAccount(){
        return context -> {
            var customer = context.body().asPojo(Customer.class);
            customerList.put(customer.getId(), customer);
            log.info("Customer with username {} created", customer.getUsername());
            context.response().setStatusCode(200).end(Json.encodePrettily(customerList));
        };
    }

    public Handler<RoutingContext> getAllCustomers(){
        return context -> {
            if (customerList.isEmpty()){
                context.response().end("List is empty");
            }
            context.response().setStatusCode(200).end(Json.encodePrettily(customerList));
        };
    }

    public Handler<RoutingContext> getCustomerById(){
        return context -> {
            int pathParam = Integer.parseInt(context.pathParam("userId"));
            if (!customerList.containsKey(pathParam)){
                log.error("Customer does not exists");

            }
            var customer = customerList.get(pathParam);
            context.response().setStatusCode(200).end(Json.encodePrettily(customer));
        };
    }

    public Handler<RoutingContext> deleteCustomerById() {
        return context -> {
            int pathParam = Integer.parseInt(context.pathParam("userId"));
            if (!customerList.containsKey(pathParam)){
                context.response().end("Customer does not exists");
            }
            customerList.remove(pathParam);
            context.response().setStatusCode(200).end("Customer successfully deleted");
        };
    }

    public Handler<RoutingContext> customerOrder() {
        return context -> {
            var path = Integer.parseInt(context.pathParam("userId"));
            var order = context.body().asPojo(MakeOrder.class);
            if (!customerList.containsKey(path)){
                log.info("There is no customer with this id");
            }
//            var customer = customerList.get(path);
//            order.setCustomerName(customer.getUsername());
            order.setId(path);
            sendRequests.customerCoffeeRequest(order);
            context.response().end(Json.encodePrettily(order));

        };
    }
}
