package org.example.service;

import jakarta.annotation.Resource;
import org.example.dto.TicketDto;
import org.example.model.Calendar;
import org.example.model.Ticket;
import org.example.model.User;
import org.example.repository.CalendarRepository;
import org.example.repository.TicketRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Resource
    private TicketRepository ticketRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CalendarRepository calendarRepository;

    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll().stream().map(ticket -> new TicketDto(
                ticket.getId(),
                ticket.getName(),
                ticket.getDescription(),
                ticket.getType(),
                ticket.getDateStart(),
                ticket.getDateEnd(),
                ticket.getCreated(),
                ticket.getOwner().getId(),
                ticket.getCalendar().getId())
        ).collect(Collectors.toList());
    }

    public TicketDto getTicketById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        TicketDto ticketDto;

        if (ticketOptional.isPresent()) {
            ticketDto = new TicketDto(
                    ticketOptional.get().getId(),
                    ticketOptional.get().getName(),
                    ticketOptional.get().getDescription(),
                    ticketOptional.get().getType(),
                    ticketOptional.get().getDateStart(),
                    ticketOptional.get().getDateEnd(),
                    ticketOptional.get().getCreated(),
                    ticketOptional.get().getOwner().getId(),
                    ticketOptional.get().getCalendar().getId()
            );
        } else {
            throw new NoSuchElementException();
        }

        return ticketDto;
    }

    public List<TicketDto> getTicketsByCalendarId(Long id) {
        return ticketRepository.findAll().stream().filter(
                ticket -> ticket.getCalendar().getId() == id ).map(
                ticket -> new TicketDto(
                        ticket.getId(),
                        ticket.getName(),
                        ticket.getDescription(),
                        ticket.getType(),
                        ticket.getDateStart(),
                        ticket.getDateEnd(),
                        ticket.getCreated(),
                        ticket.getOwner().getId(),
                        ticket.getCalendar().getId())
                ).collect(Collectors.toList());
    }

    public void saveTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        Optional<User> userOptional = userRepository.findById(ticketDto.getOwnerId());
        Optional<Calendar> calendarOptional = calendarRepository.findById(ticketDto.getCalendarId());

        if (!userOptional.isPresent()) {
            throw new NoSuchElementException();
        }

        ticket.setName(ticketDto.getName());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setType(ticketDto.getType());
        ticket.setDateStart(ticketDto.getDateStart());
        ticket.setDateEnd(ticketDto.getDateEnd());
        ticket.setOwner(userOptional.get());
        ticket.setCalendar(calendarOptional.get());

        ticketRepository.save(ticket);
    }

    public void updateTicket(TicketDto ticketDto) {
        Ticket ticket;
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketDto.getId());
        Optional<Calendar> calendarOptional = calendarRepository.findById(ticketDto.getCalendarId());

        if (ticketOptional.isPresent()) {
            ticket = ticketOptional.get();
        } else {
            throw new NoSuchElementException();
        }

        ticket.setName(ticketDto.getName());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setType(ticketDto.getType());
        ticket.setDateStart(ticketDto.getDateStart());
        ticket.setDateEnd(ticketDto.getDateEnd());
        ticket.setCalendar(calendarOptional.get());

        ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("No ticket with id: " + id);
        }
    }
}
