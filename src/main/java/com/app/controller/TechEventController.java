package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TechEventController {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/create")
    public TechEvent create(@RequestParam(value = "name", defaultValue = "Java") String name) {
        final LocalDate date = LocalDate.now();
        final TechEvent event = TechEvent.builder()
                .eventId(String.valueOf(counter.incrementAndGet()))
                .eventDate(date)
                .eventName(name)
                .build();
        redisTemplate.opsForHash().put("Redis Event", event.getEventId(), event);
        return event;
    }


    @GetMapping("/events")
    public List<TechEvent> allEvent() {
        final List<TechEvent> events = new ArrayList<>();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        final Map<String, TechEvent> allEvent = redisTemplate.opsForHash().entries("Redis Event");
        allEvent.forEach((k, v) -> {
            String text = v.getEventDate().format(formatter);
            LocalDate parsedDate = LocalDate.parse(text, formatter);
            v.setEventDate(parsedDate);
            events.add(v);
        });
        return events;
    }
}
