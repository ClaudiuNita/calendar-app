package org.example.service;

import jakarta.annotation.Resource;
import org.example.dto.CalendarDto;
import org.example.model.Calendar;
import org.example.model.User;
import org.example.repository.CalendarRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    @Resource
    private CalendarRepository calendarRepository;
    @Resource
    private UserRepository userRepository;

    public List<CalendarDto> getAllCalendars() {
        return calendarRepository.findAll().stream().map(calendar -> new CalendarDto(
                calendar.getId(),
                calendar.getName(),
                calendar.getCreated(),
                calendar.getOwner().getId())
        ).collect(Collectors.toList());
    }

    public CalendarDto getCalendarById(Long id) {
        Optional<Calendar> calendarOptional = calendarRepository.findById(id);
        CalendarDto calendarDto;

        if (calendarOptional.isPresent()) {
            calendarDto = new CalendarDto(
                    calendarOptional.get().getId(),
                    calendarOptional.get().getName(),
                    calendarOptional.get().getCreated(),
                    calendarOptional.get().getOwner().getId()
            );
        } else {
            throw new NoSuchElementException();
        }

        return calendarDto;
    }

    public void saveCalendar(CalendarDto calendarDto) {
        Calendar calendar = new Calendar();
        Optional<User> user = userRepository.findById(calendarDto.getOwnerId());

        if (!user.isPresent()) {
            throw new NoSuchElementException();
        }

        calendar.setName(calendarDto.getName());
        calendar.setOwner(user.get());

        calendarRepository.save(calendar);
    }

    public void updateCalendar(CalendarDto calendarDto) {
        Calendar calendar;
        Optional<Calendar> calendarOptional = calendarRepository.findById(calendarDto.getId());

        if (calendarOptional.isPresent()) {
            calendar = calendarOptional.get();
        } else {
            throw new NoSuchElementException();
        }

        calendar.setName(calendarDto.getName());

        calendarRepository.save(calendar);
    }

    public void deleteCalendar(Long id) {
        if (calendarRepository.existsById(id)) {
            calendarRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("No calendar with id: " + id);
        }
    }
}
