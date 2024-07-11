package com.reservation.ticket.infrastructure.repository.seat;

import com.reservation.ticket.domain.entity.Seat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeatRepositoryImplTest {

    @Autowired
    SeatJpaRepository seatJpaRepository;

    @Test
    void given_when_then() {
        // given
        List<Long> seats = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);

        // when
        List<Seat> found = seatJpaRepository.findByIdIn(seats);

        // then
        System.out.println("found = " + found);
    }

    @Test
    void gogogo() {
        // given

        // when
        List<Seat> all = seatJpaRepository.findAllByReservationIdIn(List.of(1L, 2L));

        // then
        System.out.println("all = " + all);
    }

}