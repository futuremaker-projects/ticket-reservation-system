package com.reservation.ticket.infrastructure.repository.concertSchedule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConcertScheduleJDBCRepositoryTest {

    @Autowired
    private ConcertScheduleJDBCRepository repository;

    @Test
    void test() {
        // given

        // when
        repository.bulkInsertConcertSchedules(2000000, 1000);

        // then
    }

}