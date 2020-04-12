package ru.testfield.links.controller;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.testfield.links.model.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LinkControllerTest {

    @LocalServerPort
    private int port;

    private String url;

    private final int linksToCreateNumber = 1000;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public void init(){
        url = "http://localhost:" + port+"/lnx";
    }

    @Test
    @Order(1)
    public void deleteAllTest() {
        ResponseEntity<Void> result = restTemplate.exchange(url,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    public void createTest(){
        List<Link> links = new ArrayList<>();
        for(int i=0; i<linksToCreateNumber; i++){
            links.add(new Link(new ObjectId(),
                    "http://link"+i+".ru",
                    String.valueOf(i),
                    "desc"+i)
            );
        }
        for(Link link: links) {
            HttpEntity<Link> request = new HttpEntity<>(link);
            Link createdLink = restTemplate.postForObject(url, request, Link.class);
            assertEquals(link, createdLink);
        }
    }

    @Test
    @Order(3)
    public void countTest() {
        ResponseEntity<Integer> countResponse = restTemplate.getForEntity(url + "/count", Integer.class);
        assertEquals(Integer.valueOf(linksToCreateNumber), countResponse.getBody());
    }

    @Test
    @Order(4)
    public void listAllTest() {
        ResponseEntity<Link[]> listAllResponse = restTemplate.getForEntity(url, Link[].class);
        Link[] links = listAllResponse.getBody();
        if(links==null){
            throw new RuntimeException("Unable to retrieve links from storage!");
        }else {
            assertEquals(linksToCreateNumber,links.length);
        }
    }

    @Test
    @Order(5)
    public void getByIdTest() {
        Link linkToProcess = getRandomLinkToProcess();
        ResponseEntity<Link> getByIdResponse =
                restTemplate.getForEntity(url+"/"+linkToProcess.get_id(), Link.class);

        Link foundById = getByIdResponse.getBody();
        assertEquals(linkToProcess,foundById);
    }

    @Test
    @Order(5)
    public void deleteByIdTest() {
        Link linkToProcess = getRandomLinkToProcess();
        ResponseEntity<Void> result = restTemplate.exchange(url+"/"+linkToProcess.get_id(),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(6)
    public void updateById(){
        final String testString = "TESTINGSTRING: "+ UUID.randomUUID();
        Link linkToProcess = getRandomLinkToProcess();
        linkToProcess.setDescription(testString);
        ResponseEntity<Link> result = restTemplate.exchange(url+"/"+linkToProcess.get_id(),
                HttpMethod.PUT,
                new HttpEntity<>(linkToProcess),
                Link.class);
        assertEquals(testString, result.getBody().getDescription());
    }

    private Link getRandomLinkToProcess() {
        ResponseEntity<Link[]> listAllResponse = restTemplate.getForEntity(url, Link[].class);
        Link[] links = listAllResponse.getBody();
        if(links==null){
            throw new RuntimeException("Unable to retrieve links from storage!");
        }else {
            Random random = new Random();
            int randomIndex = random.nextInt(links.length);
            return links[randomIndex];
        }
    }

}
