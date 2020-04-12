package ru.testfield.links.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.testfield.links.model.Link;
import ru.testfield.links.repository.LinkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final LinkRepository linkRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initMongoDb(){
        List<Link> links = new ArrayList<>();
        for(int i=0;i<10;i++){
            links.add(new Link(UUID.randomUUID(),
                    "http://link"+i+".ru",
                    String.valueOf(i),
                    "desc"+i)
            );
        }

        Flux<Link> linkFlux = linkRepository.deleteAll().thenMany(linkRepository.saveAll(links));
        linkFlux.subscribe();
    }

}
