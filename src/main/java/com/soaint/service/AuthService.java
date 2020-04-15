package com.soaint.service;

import com.soaint.domain.Auth;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.Arrays;

@Service
public class AuthService {

    /**
     * Service is used for Auth the user
     * @param user authorization data
     * @return OAuth2 object
     */
    public String login(Auth user) {
        // ********************* Get Token *******************************
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();

        // According OAuth documentation we need to send the client id and secret key in the header for authentication
        String credentials = "soaint-client" + ":" + "$2a$04$e/c1/RfsWuThaWFCrcCuJeoyvwCV0URN/6Pn9ZFlrtIWaU/vj/BfG";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        String access_token_url = "http://localhost:8080/api/v1/auth/token";
        access_token_url += "?username=admin" + "&password=password"  + "&grant_type=password";

        response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(response.getBody());
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        String token = node.path("access_token").asText();
        String ttl = node.path("expires_in").asText();
        //System.out.println("Access Token Response ---------" + response.getBody());
        // ********************* Get Token *******************************
        return token;
    }

}