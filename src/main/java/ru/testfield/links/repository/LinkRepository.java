package ru.testfield.links.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.testfield.links.model.Link;

import java.util.UUID;

@Repository
public interface LinkRepository extends ReactiveCrudRepository<Link, UUID> {
}
