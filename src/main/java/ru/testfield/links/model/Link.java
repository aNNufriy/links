package ru.testfield.links.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Link {
    private final UUID uuid;
    private final String targetLink;
    private final String shortLink;
    private final String description;
}
