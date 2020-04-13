package ru.testfield.links.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
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
    public Mono<Long> count(){
        return linkRepository.count();
    }

    @RequestMapping(value = {"","/"}, method = RequestMethod.GET)
    public Flux<Link> listAll(){
        return linkRepository.findAll();
    }

    @RequestMapping(value = {"","/"}, method = RequestMethod.DELETE)
    public void deleteAll(){
        linkRepository.deleteAll().subscribe();
    }

    @RequestMapping(value = {"","/"}, method = RequestMethod.POST)
    public Mono<Link> create(@RequestBody Link link) {
        return linkRepository.save(link);
    }

    @RequestMapping(value = "/{linkId}", method = RequestMethod.GET)
    public Mono<Link> getById(@PathVariable ObjectId linkId){
        return linkRepository.findById(linkId);
    }

    @RequestMapping(value = "/{linkId}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable ObjectId linkId){
        linkRepository.deleteById(linkId).subscribe();
    }

    @RequestMapping(value = "/{linkId}", method = RequestMethod.PUT)
    public Mono<Link> updateById(@PathVariable ObjectId linkId, @RequestBody Link link) {
        if(link.get_id()==null){
            link.set_id(linkId);
        }
        return linkRepository.save(link);
    }

}