package com.eventpulse.services;

import com.eventpulse.models.Subscription;
import com.eventpulse.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription subscribeToEvent(String userId, String eventId) {
        Subscription sub = new Subscription();
        sub.setUserId(userId);
        sub.setEventId(eventId);
        return subscriptionRepository.save(sub);
    }

    public Subscription subscribeToCategory(String userId, String category) {
        Subscription sub = new Subscription();
        sub.setUserId(userId);
        sub.setCategory(category);
        return subscriptionRepository.save(sub);
    }

    public List<Subscription> getUserSubscriptions(String userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public void unsubscribe(String id) {
        subscriptionRepository.deleteById(id);
    }
}