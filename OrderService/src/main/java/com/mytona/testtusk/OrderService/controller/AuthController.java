package com.mytona.testtusk.OrderService.controller;

import com.mytona.testtusk.OrderService.configuration.jwt.JwtProvider;
import com.mytona.testtusk.OrderService.entity.users.Account;
import com.mytona.testtusk.OrderService.exception.BadRequestException;
import com.mytona.testtusk.OrderService.pojo.AuthRequest;
import com.mytona.testtusk.OrderService.pojo.AuthResponse;
import com.mytona.testtusk.OrderService.pojo.RegistrationRequest;
import com.mytona.testtusk.OrderService.service.AccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AccountService accountService;

    private final JwtProvider jwtProvider;

    public AuthController(AccountService accountService, JwtProvider jwtProvider) {
        this.accountService = accountService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/auth")
    public AuthResponse auth(@Validated @RequestBody AuthRequest request) {
        try {
            Account account = this.accountService.findByLoginAndPassword(request.getLogin(), request.getPassword());
            String token = jwtProvider.generateToken(account.getUsername());
            return new AuthResponse(token, account.getId(), account.getFirstName() + " " + account.getLastName(), account.getEmail(),
                    account.getPhone());
        } catch (Exception e) {
            throw new BadRequestException("Username or Password is not correct");
        }

    }

    @PostMapping("/registration")
    public AuthResponse registration (@Validated @RequestBody RegistrationRequest request) {
        try {
            accountService.saveAccount(new Account(request.getUsername(), request.getEmail(), request.getPassword(),
                    request.getFirstName(), request.getLastName(), request.getPhone()));
            Account account = this.accountService.findByLoginAndPassword(request.getUsername(), request.getPassword());
            String token = jwtProvider.generateToken(account.getUsername());
            return new AuthResponse(token, account.getId(), account.getFirstName() + " " + account.getLastName(), account.getEmail(),
                    account.getPhone());
        } catch (Exception e) {
            throw new BadRequestException("Username and Email must be unique");
        }

    }

}
