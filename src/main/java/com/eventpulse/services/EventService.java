package com.eventpulse.services;

import com.eventpulse.models.Event;
import com.eventpulse.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Event createEvent(Event event, String username) {
        event.setCreatedBy(username);
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        Event saved = eventRepository.save(event);
        // Notify subscribers
        kafkaTemplate.send("event-notifications", "Event created: " + saved.getId());
        return saved;
    }

    public Event updateEvent(String id, Event event, String username) {
        Event existing = eventRepository.findById(id).orElseThrow();
        event.setId(id);
        event.setUpdatedAt(LocalDateTime.now());
        Event saved = eventRepository.save(event);
        // Notify subscribers
        kafkaTemplate.send("event-notifications", "Event updated: " + saved.getId());
        return saved;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id).orElseThrow();
    }

    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}