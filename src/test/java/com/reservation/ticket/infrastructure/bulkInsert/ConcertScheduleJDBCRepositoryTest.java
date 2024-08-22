package com.reservation.ticket.infrastructure.bulkInsert;

import com.reservation.ticket.infrastructure.repository.concertSchedule.ConcertScheduleJDBCRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConcertScheduleJDBCRepositoryTest {

    @Autowired
    private ConcertScheduleJDBCRepository repository;

    @Test
    void test() {
        // given

        // when
        repository.bulkInsertConcertSchedules(100_000, 1000);

        // then
    }

}