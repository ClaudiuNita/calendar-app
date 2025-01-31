package org.example.controller;

import jakarta.annotation.Resource;
import org.example.dto.CalendarDto;
import org.example.service.CalendarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@CrossOrigin(origins = "http://localhost:4200")
public class CalendarController {

    @Resource
    private CalendarService calendarService;

    @GetMapping
    public List<CalendarDto> getAllCalendars() {
        return calendarService.getAllCalendars();
    }

    @GetMapping("/{id}")
    public CalendarDto getCalendarById(@PathVariable Long id) {
        return calendarService.getCalendarById(id);
    }

    @PostMapping
    public void saveCalendar(@RequestBody CalendarDto calendarDto) {
        calendarService.saveCalendar(calendarDto);
    }

    @PutMapping
    public void updateCalendar(@RequestBody CalendarDto calendarDto) {
        calendarService.updateCalendar(calendarDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCalendar(@PathVariable Long id) {
        calendarService.deleteCalendar(id);
    }
}
