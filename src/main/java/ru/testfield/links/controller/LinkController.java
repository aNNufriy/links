package ru.testfield.links.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.testfield.links.model.Link;
import ru.testfield.links.repository.LinkRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/lnx")
public class LinkController {

    private final LinkRepository linkRepository;

    @RequestMapping("/count")
    public Mono<Long> countLinks(){
        Mono<Long> linksNumberMono = linkRepository.count();
        return linksNumberMono;
    }

    @RequestMapping("/list")
    public Flux<Link> listAll(){
        return linkRepository.findAll();
    }

    @RequestMapping(value = "/",method = RequestMethod.DELETE)
    public void deleteById(){

    }

}