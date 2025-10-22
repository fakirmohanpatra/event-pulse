package com.eventpulse.controllers;

import com.eventpulse.models.Subscription;
import com.eventpulse.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Subscription> subscribeToEvent(@PathVariable String eventId) {
        Subscription sub = subscriptionService.subscribeToEvent("anonymous", eventId);
        return ResponseEntity.ok(sub);
    }

    @PostMapping("/category/{category}")
    public ResponseEntity<Subscription> subscribeToCategory(@PathVariable String category) {
        Subscription sub = subscriptionService.subscribeToCategory("anonymous", category);
        return ResponseEntity.ok(sub);
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptions() {
        List<Subscription> subs = subscriptionService.getUserSubscriptions("anonymous");
        return ResponseEntity.ok(subs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unsubscribe(@PathVariable String id) {
        subscriptionService.unsubscribe(id);
        return ResponseEntity.noContent().build();
    }
}