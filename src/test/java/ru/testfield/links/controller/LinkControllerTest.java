package ru.testfield.links.controller;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.testfield.links.model.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkControllerTest extends AbstractControllerTest {

    @Test
    @Order(1)
    public void deleteAllTest() {
        ResponseEntity<Void> result = restTemplate.exchange(url + "/lnx",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class);

        assertEquals(HttpStatus.OK,result.getStatusCode());
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
            Link createdLink = restTemplate.postForObject(url+"/lnx", new HttpEntity<>(link), Link.class);
            assertEquals(link, createdLink);
        }
    }

    @Test
    @Order(3)
    public void countTest() {
        ResponseEntity<Integer> countResponse = restTemplate.getForEntity(url+"/lnx/count", Integer.class);
        assertEquals(Integer.valueOf(linksToCreateNumber), countResponse.getBody());
    }

    @Test
    @Order(4)
    public void listAllTest() {
        ResponseEntity<Link[]> listAllResponse = restTemplate.getForEntity(url+"/lnx", Link[].class);
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
                restTemplate.getForEntity(url+"/lnx/"+linkToProcess.get_id(), Link.class);

        Link foundById = getByIdResponse.getBody();
        assertEquals(linkToProcess,foundById);
    }

    @Test
    @Order(6)
    public void deleteByIdTest() {
        Link linkToProcess = getRandomLinkToProcess();
        ResponseEntity<Void> result = restTemplate.exchange(url+"/lnx/"+linkToProcess.get_id(),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class);
        assertEquals(HttpStatus.OK,result.getStatusCode());

        ResponseEntity<Integer> countResponse = restTemplate.getForEntity(url+"/lnx/count", Integer.class);
        assertEquals(Integer.valueOf(linksToCreateNumber-1), countResponse.getBody());
    }

    @Test
    @Order(7)
    public void updateById(){
        final String testString = "TESTINGSTRING: "+ UUID.randomUUID();
        Link linkToProcess = getRandomLinkToProcess();
        linkToProcess.setDescription(testString);
        ResponseEntity<Link> result = restTemplate.exchange(url+"/lnx/"+linkToProcess.get_id(),
                HttpMethod.PUT,
                new HttpEntity<>(linkToProcess),
                Link.class);
        if(result.getBody()!=null) {
            assertEquals(testString, result.getBody().getDescription());
        }else{
            throw new RuntimeException("Unable to recieve response body!");
        }
    }

}
