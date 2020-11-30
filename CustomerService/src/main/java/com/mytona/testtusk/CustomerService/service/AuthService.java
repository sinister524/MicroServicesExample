package com.mytona.testtusk.CustomerService.service;

import com.mytona.testtusk.CustomerService.pojo.auth.AuthInfo;
import com.mytona.testtusk.CustomerService.pojo.auth.AuthRequest;
import com.mytona.testtusk.CustomerService.pojo.auth.RegistrationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${url}")
    private String url;

    public AuthInfo logIn (AuthRequest request) {
        return restTemplate.postForObject(url + "/auth", request, AuthInfo.class);
    }

    public AuthInfo registration (RegistrationRequest request) {
        return restTemplate.postForObject(url + "/registration", request, AuthInfo.class);
    }
}
