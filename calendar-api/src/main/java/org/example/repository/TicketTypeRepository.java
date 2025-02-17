package org.example.repository;

import org.example.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

    List<TicketType> findTicketTypesByCalendarId(Long calendarId);
}
