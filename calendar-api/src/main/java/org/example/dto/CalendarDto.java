package org.example.dto;

import java.time.LocalDateTime;

public class CalendarDto {

    private Long id;
    private String name;
    private LocalDateTime created;
    private Long ownerId;

    public CalendarDto() {
    }

    public CalendarDto(Long id, String name, LocalDateTime created, Long ownerId) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
