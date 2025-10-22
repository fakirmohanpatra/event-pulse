package com.eventpulse.controllers;

import com.eventpulse.models.Subscription;
import com.eventpulse.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Subscription> subscribeToEvent(@PathVariable String eventId, Authentication authentication) {
        String userId = authentication.getName(); // assuming username is id, or need to get user id
        Subscription sub = subscriptionService.subscribeToEvent(userId, eventId);
        return ResponseEntity.ok(sub);
    }

    @PostMapping("/category/{category}")
    public ResponseEntity<Subscription> subscribeToCategory(@PathVariable String category, Authentication authentication) {
        String userId = authentication.getName();
        Subscription sub = subscriptionService.subscribeToCategory(userId, category);
        return ResponseEntity.ok(sub);
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptions(Authentication authentication) {
        String userId = authentication.getName();
        List<Subscription> subs = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unsubscribe(@PathVariable String id) {
        subscriptionService.unsubscribe(id);
        return ResponseEntity.noContent().build();
    }
}