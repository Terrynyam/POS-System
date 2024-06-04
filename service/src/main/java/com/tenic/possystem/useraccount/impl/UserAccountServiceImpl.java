package com.tenic.possystem.useraccount.impl;

import com.tenic.possystem.auth.LoginResponse;
import com.tenic.possystem.auth.RoleDetails;
import com.tenic.possystem.country.Country;
import com.tenic.possystem.country.CountryRepository;
import com.tenic.possystem.exceptions.FailedToProcessRequestException;
import com.tenic.possystem.exceptions.FileAlreadyExistsException;
import com.tenic.possystem.exceptions.FileDoesNotExistsException;
import com.tenic.possystem.exceptions.IncorrectUsernameOrPasswordException;
import com.tenic.possystem.useraccount.UserAccount;
import com.tenic.possystem.useraccount.UserAccountRepository;
import com.tenic.possystem.useraccount.UserAccountRequest;
import com.tenic.possystem.useraccount.UserAccountService;
import com.tenic.possystem.utils.UserAccountValidation;
import com.tenic.possystem.utils.enums.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @author Terrance Nyamfukudza
 * 30/5/2024
 */
@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final CountryRepository countryRepository;
    private final RestTemplate restTemplate;

    @Value("${keycloak.token-uri}")
    private String tokenUrl;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.grant-type}")
    private String grantType;
    @Value("${keycloak.admin-client-id}")
    private String adminClientId;
    @Value("${keycloak.admin-username}")
    private String adminUsername;
    @Value("${keycloak.admin-password}")
    private String adminPassword;
    @Value("${keycloak.masterUrl}")
    private String masterUrl;
    @Value("${keycloak.client-user-uri}")
    private String clientUserUrl;
    @Value("${keycloak.client-uuid}")
    private String clientUUID;
    @Value("${default-user-password.password}")
    private String userPassword;
    String userId;
    @Override
    public UserAccount create(UserAccountRequest request) {
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new FileDoesNotExistsException("Invalid country"));

        Optional<UserAccount> userEmail = userAccountRepository
                .findUserAccountByEmail(request.getEmail());
        if (userEmail.isPresent())
            throw new FileAlreadyExistsException("Email is already in use");

        log.info("Validating a user {}", request);

        UserAccountValidation.validateUser(request);

        log.info("Creating a user {}", request);
        UserAccount userAccount = getUserId(request,country);
        assignUserRole(request);
        return userAccount;
    }

    @Override
    public UserAccount getUser(String principal) {
        var user = userAccountRepository.findByUsername(principal);
        if (user.isEmpty())
            throw new FileDoesNotExistsException("User not found");
        return user.get();
    }

    @Override
    public String changePassword(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<UserAccount> delete(Long id) {
        return null;
    }

    @Override
    public UserAccount setApproval(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("UserAccount not found"));

        userAccount.setIsApproved(Boolean.TRUE);
        userAccount.setStatus(Status.ACTIVE);
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount deactivate(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("UserAccount not found"));
        userAccount.setStatus(Status.DEACTIVATED);
        return userAccountRepository.save(userAccount);
    }

    @Override
    public List<UserAccount> getByApprovalStatus(Boolean isApproved) {
        return userAccountRepository.findByIsApproved(isApproved);
    }

    @Override
    public List<UserAccount> getByStatus(Status status) {
        return userAccountRepository.findByStatus(status);
    }

    private LoginResponse adminToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grantType);
        map.add("client_id", adminClientId);
        map.add("username", adminUsername);
        map.add("password", adminPassword);

        HttpEntity<LinkedMultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        log.info("request : {}", httpEntity);
        try {
            return restTemplate.postForObject(masterUrl, httpEntity, LoginResponse.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new IncorrectUsernameOrPasswordException("password or username is incorrect");
        } catch (Exception e) {
            throw new FailedToProcessRequestException("unable to process request");
        }
    }
    private String roleId(UserAccountRequest request) {
        String token;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        assert adminToken() != null;
        token = adminToken().getAccess_token();

        headers.setBearerAuth(token);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        log.info("request : {}", requestEntity);

        try {
            RoleDetails roleDetails = restTemplate.exchange("http://localhost:8080/admin/realms/POS-System/" +
                            "clients/" + clientUUID + "/roles/" + request.getUserType(), HttpMethod.GET, requestEntity,
                    RoleDetails.class).getBody();

            assert roleDetails != null;
            return roleDetails.getId();

        } catch (HttpClientErrorException httpClientErrorException) {
            throw new IncorrectUsernameOrPasswordException("password or username is incorrect");
        } catch (Exception e) {
            throw new FailedToProcessRequestException("unable to process request");
        }
    }
    private UserAccount getUserId(UserAccountRequest request,Country country){
        String token;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        assert adminToken() != null;
        token = adminToken().getAccess_token();

        headers.setBearerAuth(token);
        HttpEntity<String> requestEntity = getStringHttpEntity(request, headers, userPassword);
        log.info("request : {}", requestEntity);
        try {
            ResponseEntity<String> responses = restTemplate.postForEntity(clientUserUrl, requestEntity, String.class);

            userId = responses.getHeaders().getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        } catch (HttpClientErrorException httpClientErrorException) {
            throw new IncorrectUsernameOrPasswordException("password or username is incorrect");
        } catch (Exception e) {
            throw new FailedToProcessRequestException("unable to process request");
        }
        var user = UserAccount.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getEmail())
                .userType(request.getUserType())
                .dateOfBirth(request.getDateOfBirth())
                .country(country)
                .status(Status.INACTIVE)
                .isApproved(Boolean.FALSE)
                .build();
        return userAccountRepository.save(user);
    }

    private static HttpEntity<String> getStringHttpEntity(UserAccountRequest request, HttpHeaders headers, String userPassword) {
        String requestBody = "{\n" +
                "    \"username\":\"" + request.getEmail() + "\",\n" +
                "    \"email\":\"" + request.getEmail() + "\",\n" +
                "    \"lastName\":\"" + request.getLastName() + "\",\n" +
                "    \"firstName\":\"" + request.getFirstname() + "\",\n" +
                "    \"enabled\":\"true\",\n" +
                "    \"credentials\":[{\"value\":\"" + userPassword + "\",\"type\":\"password\",\"temporary\":false}]\n" +
                "}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return requestEntity;
    }

    private void assignUserRole(UserAccountRequest request) {
        String token;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        assert adminToken() != null;
        token = adminToken().getAccess_token();

        headers.setBearerAuth(token);
        // JSON request body for role assignment

        String requestBody = "[{"
                + "\"id\":\"" + roleId(request) + "\","
                + "\"name\":\"" +request.getUserType().name()+ "\","
                + "\"composite\":false,"
                + "\"clientRole\":false,"
                + "\"containerId\":\"" + clientUUID + "\""
                + "}]";
        log.info("request creating role Body");

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);


        try {
            restTemplate.postForEntity(clientUserUrl + "/" + userId + "/role-mappings/clients/" + clientUUID
                    , requestEntity, String.class);


        } catch (HttpClientErrorException httpClientErrorException) {
            throw new IncorrectUsernameOrPasswordException("password or username is incorrect");
        } catch (Exception e) {
            throw new FailedToProcessRequestException("unable to process request");
        }

    }

}
