package com.mytona.testtusk.OrderService.exception;

public class OrderException extends RuntimeException{

    public OrderException(long id, String message) {
        super("Order with ID - "+ id + " " + message);
    }
}
