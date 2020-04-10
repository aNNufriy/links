package ru.testfield.links.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import ru.testfield.links.repository.LinkRepository;

import java.net.URI;

@RestController
@RequestMapping(value="/lnx")
@RequiredArgsConstructor
public class LinkController {

    private final LinkRepository linkRepository;

    @RequestMapping("/count")
    public Mono<Long> countLinks(){
        Mono<Long> linksNumberMono = linkRepository.count();
        return linksNumberMono;
    }
}