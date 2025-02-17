package org.example.controller;

import jakarta.annotation.Resource;
import org.example.dto.TicketDto;
import org.example.model.TicketType;
import org.example.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Resource
    private TicketService ticketService;

    @GetMapping
    private List<TicketDto> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    private TicketDto getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @GetMapping("/calendar/{id}")
    private List<TicketDto> getTicketsByCalendarId(@PathVariable Long id) {
        return ticketService.getTicketsByCalendarId(id);
    }

    @GetMapping("/type/calendar/{id}")
    private List<TicketType> getTicketTypesByCalendarId(@PathVariable Long id) {
        return ticketService.getTicketTypesByCalendarId(id);
    }

    @PostMapping
    private void saveTicket(@RequestBody TicketDto ticketDto) {
        ticketService.saveTicket(ticketDto);
    }

    @PutMapping()
    private void updateTicket(@RequestBody TicketDto ticketDto) {
        ticketService.updateTicket(ticketDto);
    }

    @DeleteMapping("/{id}")
    private void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }
}
