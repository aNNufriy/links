package ru.testfield.links.controller;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ru.testfield.links.model.Link;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedirectControllerTest extends AbstractControllerTest {
    @Test
    @Order(1)
    public void redirectTest() {
        Link linkToProcess = getRandomLinkToProcess();
        HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
        ResponseEntity<Void> response = restTemplate.exchange(url + "/" + linkToProcess.getShortLink(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Void.class);
        List<String> location = response.getHeaders().get("Location");
        if (location != null) {
            assertEquals(1, location.size());
            assertEquals(linkToProcess.getTargetLink(), location.get(0));
        } else {
            throw new RuntimeException("Redirect headers not set!");
        }
    }
}