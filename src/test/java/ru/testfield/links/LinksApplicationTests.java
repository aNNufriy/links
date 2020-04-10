package ru.testfield.links;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import ru.testfield.links.model.Link;
import ru.testfield.links.repository.LinkRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LinksApplicationTests {

    LinkRepository linkRepository;

    @Autowired
    LinksApplicationTests (LinkRepository linkRepository){
        this.linkRepository = linkRepository;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void givenAccount_whenSave_thenSaveAccount() {

        linkRepository.deleteAll()
                .as(StepVerifier::create)
                .verifyComplete();

        linkRepository.save(new Link(UUID.randomUUID(), null, null, null))
                .as(StepVerifier::create)
                .assertNext(link -> assertNotNull(link.getUuid()))
                .expectComplete()
                .verify();
    }

}
