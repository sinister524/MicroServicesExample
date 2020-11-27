package com.mytona.testtusk.CustomerService.mapper;

import com.mytona.testtusk.CustomerService.dto.OrderInfoDto;
import com.mytona.testtusk.CustomerService.pojo.Cart;
import com.mytona.testtusk.CustomerService.pojo.OrderInfo;
import com.mytona.testtusk.CustomerService.pojo.Pair;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderInfoMapper {
    public OrderInfo dtoToPojo (OrderInfoDto dto) {
        Cart cart = new Cart();
        cart.setProducts(dto.getProducts().stream()
                .collect(Collectors.toMap(Pair::getProduct, Pair::getQuantity)));
        return new OrderInfo(dto.getId(), dto.getCustomerName(), dto.getCustomerEmail(),
                dto.getCustomerPhone(), dto.isConfirmed(), dto.isPaid(), dto.isDelivered(),
                cart);
    }
}
