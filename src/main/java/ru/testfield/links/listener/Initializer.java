package ru.testfield.links.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initializer {

    @EventListener(ApplicationReadyEvent.class)
    public void initMongoDb(){

    }

}
