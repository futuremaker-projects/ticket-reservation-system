package com.reservation.ticket.infrastructure.repository.scheduleSeat;

import com.reservation.ticket.domain.entity.complex.ScheduleSeat;
import com.reservation.ticket.domain.repository.ScheduleSeatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleSeatRepositoryImplTest {

    @Autowired
    ScheduleSeatRepository scheduleSeatRepository;

    @Test
    void given_when_then() {
        // given
        List<Long> list = List.of(1L, 2L);

        // when
        List<ScheduleSeat> scheduleSeats = scheduleSeatRepository.findAllByReservationIdIn(list);

        // then
        for (ScheduleSeat scheduleSeat : scheduleSeats) {
            System.out.println("scheduleSeat.getId().getConcertScheduleId() = " + scheduleSeat.getId().getConcertScheduleId());
            System.out.println("scheduleSeat.getId().getSeatId() = " + scheduleSeat.getId().getSeatId());
        }
    }

}