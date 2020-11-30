package com.mytona.testtusk.OrderService.controller;

import com.mytona.testtusk.OrderService.configuration.jwt.JwtProvider;
import com.mytona.testtusk.OrderService.dto.OrderCreateDto;
import com.mytona.testtusk.OrderService.dto.OrderInfoDto;
import com.mytona.testtusk.OrderService.entity.Order;
import com.mytona.testtusk.OrderService.mapper.OrderCreateMapper;
import com.mytona.testtusk.OrderService.mapper.OrderInfoMapper;
import com.mytona.testtusk.OrderService.service.AccountService;
import com.mytona.testtusk.OrderService.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderInfoMapper infoMapper;

    private final OrderCreateMapper createMapper;

    private final OrderService service;

    private final JwtProvider jwtProvider;

    @GetMapping("/get")
    public List<OrderInfoDto> getAllOrders (@RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtProvider.getLoginFromToken(token);
        return service.getAll(username).stream()
                .map(infoMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public OrderInfoDto getOrder (@PathVariable Long id, @RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtProvider.getLoginFromToken(token);
        return infoMapper.entityToDto(service.get(id, username));
    }

    @PostMapping(value = "/add")
    public OrderInfoDto addOrder (@Validated @NotNull @RequestBody OrderCreateDto dto,
                                  @RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtProvider.getLoginFromToken(token);
        return infoMapper.entityToDto(service.save(username, createMapper.dtoToEntity(dto), dto.getProducts()));
    }

    @PostMapping("/edit/{id}")
    public OrderInfoDto editOrder (@PathVariable Long id, @Validated @NotNull @RequestBody OrderCreateDto dto,
                                   @RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtProvider.getLoginFromToken(token);
        return infoMapper.entityToDto(service.edit(id, createMapper.dtoToEntity(dto), username, dto.getProducts()));
    }

    @GetMapping("/confirm/{id}")
    public OrderInfoDto confirmOrder (@PathVariable Long id, @RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtProvider.getLoginFromToken(token);
        return infoMapper.entityToDto(service.confirm(id, username));
    }

    @GetMapping("/pay/{id}")
    public OrderInfoDto payOrder (@PathVariable Long id, @RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtProvider.getLoginFromToken(token);
        return infoMapper.entityToDto(service.pay(id, username));
    }

    @GetMapping("/deliver/{id}")
    public OrderInfoDto deliverOrder (@PathVariable Long id, @RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtProvider.getLoginFromToken(token);
        return infoMapper.entityToDto(service.deliver(id, username));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder (@PathVariable Long id, @RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String username = jwtProvider.getLoginFromToken(token);
        service.delete(id, username);
    }
}
