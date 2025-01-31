package org.example.dto;

import java.time.LocalDateTime;

public class TicketDto {

    private Long id;
    private String name;
    private String description;
    private String type;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private LocalDateTime created;
    private Long ownerId;
    private Long calendarId;

    public TicketDto() {
    }

    public TicketDto(Long id, String name, String description, String type, LocalDateTime dateStart, LocalDateTime dateEnd, LocalDateTime created, Long ownerId, Long calendarId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.created = created;
        this.ownerId = ownerId;
        this.calendarId = calendarId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
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

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }
}
