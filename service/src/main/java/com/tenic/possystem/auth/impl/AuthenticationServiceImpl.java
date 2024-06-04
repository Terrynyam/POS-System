package com.tenic.possystem.auth.impl;

import com.tenic.possystem.auth.AuthenticationService;
import com.tenic.possystem.auth.LoginRequest;
import com.tenic.possystem.auth.LoginResponse;
import com.tenic.possystem.exceptions.FailedToProcessRequestException;
import com.tenic.possystem.exceptions.IncorrectUsernameOrPasswordException;
import com.tenic.possystem.useraccount.UserAccount;
import com.tenic.possystem.useraccount.UserAccountRepository;
import com.tenic.possystem.utils.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * @author Terrance Nyamfukudza
 * 4/6/2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserAccountRepository userAccountRepository;
    private final RestTemplate restTemplate;

    @Value("${keycloak.token-uri}")
    private String tokenUrl;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.grant-type}")
    private String grantType;
    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<UserAccount> userAccountDetails = userAccountRepository.findByUsername(request.getUsername());
        if(userAccountDetails.isEmpty()){
            throw new FailedToProcessRequestException("User not found");
        }
        if(!userAccountDetails.get().getStatus().equals(Status.ACTIVE)){
            throw new FailedToProcessRequestException("User not active");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LoginResponse response = null;
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grantType);
        map.add("client_id", clientId);
        map.add("username", request.getUsername());
        map.add("password", request.getPassword());


        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        log.info("request : {}", httpEntity);
        try {
            response = restTemplate.postForObject(tokenUrl, httpEntity, LoginResponse.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new IncorrectUsernameOrPasswordException("Invalid credentials");
        } catch (Exception e) {
            throw new FailedToProcessRequestException("unable to process request");
        }
        return response;
    }
}
