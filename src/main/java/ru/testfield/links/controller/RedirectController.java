package ru.testfield.links.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;
import ru.testfield.links.repository.LinkRepository;

@Controller
@RequiredArgsConstructor
public class RedirectController {

    private final LinkRepository linkRepository;

    @RequestMapping(value = "/{shortLink}")
    public Mono<RedirectView> redirect(@PathVariable String shortLink) {
        return linkRepository.findByShortLink(shortLink)
                .map(link -> {
                    RedirectView redirectView = new RedirectView();
                    redirectView.setUrl(link.getTargetLink());
                    return redirectView;
                });
    }
}
