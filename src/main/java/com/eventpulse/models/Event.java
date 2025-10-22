package com.eventpulse.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "events")
public class Event {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private String category;
    private String createdBy;
    private List<String> subscribers; // user ids
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}