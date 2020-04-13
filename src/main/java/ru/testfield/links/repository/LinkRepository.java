package ru.testfield.links.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.testfield.links.model.Link;

@Repository
public interface LinkRepository extends ReactiveCrudRepository<Link, ObjectId> {
    Mono<Link> findByShortLink(String shortLink);
}