package com.pigorv.springcloud.orders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequestMapping
@RestController
public class OrdersController {
    private List<Order> orderList = new ArrayList<>();

    private final UserClient userClient;
    private final ProductClient productClient;
    private final NotificationClient notificationClient;

    public OrdersController(UserClient userClient, ProductClient productClient, NotificationClient notificationClient) {
        this.userClient = userClient;
        this.productClient = productClient;
        this.notificationClient = notificationClient;
    }

    @GetMapping
    public String health() {
        return "OK";
    }

    @PostMapping
    public ResponseEntity<Order> createNewOrder(@RequestBody Order order) {
        userClient.getUser(order.getUserName());
        notificationClient.notify(order.getUserName());
        productClient.getProduct(order.getProduct());

        orderList.add(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userName}")
    public List<String> getProductsForUser(@PathVariable String userName) {
        return orderList.stream()
                .filter(order -> userName.equals(order.getUserName()))
                .map(Order::getProduct)
                .collect(toList());
    }
}
