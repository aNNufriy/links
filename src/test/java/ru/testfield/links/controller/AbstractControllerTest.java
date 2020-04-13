package ru.testfield.links.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.testfield.links.model.Link;

import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerTest {

    @LocalServerPort
    private int port;

    protected String url;

    protected final int linksToCreateNumber = 1000;

    @Autowired
    protected TestRestTemplate restTemplate;

    @BeforeAll
    public void init() {
        url = "http://localhost:" + port;
    }

    protected Link getRandomLinkToProcess() {
        ResponseEntity<Link[]> listAllResponse = restTemplate.getForEntity(url + "/lnx", Link[].class);
        Link[] links = listAllResponse.getBody();
        if (links == null) {
            throw new RuntimeException("Unable to retrieve links from storage!");
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(links.length);
            return links[randomIndex];
        }
    }

}