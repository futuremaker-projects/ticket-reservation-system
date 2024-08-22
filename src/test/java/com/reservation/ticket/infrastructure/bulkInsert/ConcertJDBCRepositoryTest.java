package com.reservation.ticket.infrastructure.bulkInsert;

import com.reservation.ticket.infrastructure.repository.concert.ConcertJDBCRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConcertJDBCRepositoryTest {

    @Autowired
    private ConcertJDBCRepository repository;

    @Test
    void test01() {
        // given

        // when
        repository.bulkInsertConcertSchedules(100_000, 1000);

        // then
    }

}