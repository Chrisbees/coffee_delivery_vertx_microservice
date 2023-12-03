package com.chrisbees.coffeeorderingsystem.verticles;

import com.chrisbees.coffeeorderingsystem.model.OrderRequest;
import com.chrisbees.coffeeorderingsystem.model.OrderResponse;
import com.chrisbees.coffeeorderingsystem.service.OrderService;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
public class HandleOrders{

    private static final Map<Integer, OrderRequest> ordersList = new HashMap<>();
    private final Logger LOG = LoggerFactory.getLogger(HandleOrders.class);
    private final OrderService orderService;

    public Handler<RoutingContext> handlePutRequest() {
       return ctx -> {
           var orderResponse = ctx.body().asPojo(OrderResponse.class);
           var userId = Integer.parseInt(ctx.pathParam("userId"));
           if (!ordersList.containsKey(userId)){
               LOG.error("User with {} does not exists", userId);
               return;
           }else {
               long deliveryTime = orderResponse.getDuration();
               long currentTime = LocalTime.now().toSecondOfDay();
               AtomicLong remainingTime = new AtomicLong(deliveryTime);

               if (remainingTime.get() == 0){
                   LOG.info("Your order is ready");
                   ctx.response().setStatusCode(200).end("Your order is ready");
                   return;
               }else {
                   ctx.vertx().setPeriodic(3000, action -> {
                       remainingTime.addAndGet(-3);
                           String updates = String.format("Your order will be ready in " + remainingTime);
                           orderResponse.setMessage(updates);
                           LOG.info(updates);
                           orderService.sendOrderUpdates(updates);
                           if (remainingTime.get() < 3) {
                                   remainingTime.addAndGet(-1);
                                   if(remainingTime.get() >=0){
                                       String updates1 = String.format("Your order will be ready in " + remainingTime);
                                       orderResponse.setMessage(updates1);
                                       LOG.info(updates1);
                                       orderService.sendOrderUpdates(updates1);
                                   }
                               if (remainingTime.get() <= 0){
                                   LOG.info("Order for user {} is ready", userId);
                                   ctx.vertx().cancelTimer(action); // Stop the periodic updates
                                   String fianlUpdate = "Your order is ready";
                                   orderService.sendOrderUpdates(fianlUpdate);
                               }
                           }
                   });
                   ctx.response().setStatusCode(200).end("Updates Sent");
               }
           }
        };
    }

   public Handler<RoutingContext> handleGetOrders(){
        return ctx -> {
            if (ordersList.isEmpty()){
                ctx.response().setStatusCode(400).end("There are no current orders");
                LOG.error("there are no current orders");
            }
            ctx.response().setStatusCode(200).end(Json.encodePrettily(ordersList));
        };
   }

//    public Handler<RoutingContext> handleAddOrders(){
//        return ctx -> {
//          var order = ctx.body().asPojo(OrderRequest.class);
//            ordersList.put(order.getCustomerName(), order);
//            ctx.response().setStatusCode(200).end(Json.encodePrettily(order));
//            LOG.info("List of orders {}", ordersList);
//        };
//    }

    public void processOrders(OrderRequest orderRequest) {
        ordersList.put(orderRequest.getId(), orderRequest);
    }
}
