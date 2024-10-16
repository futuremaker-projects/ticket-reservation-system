package com.reservation.ticket.dummy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAccountDataCreator {

    @Autowired
    private BulkInsertJDBCRepository repository;

    /**
     *  1000명의 사용자 더미데이터 생성
     */
    @Test
    void test01() {
        // given

        // when
        repository.bulkInsertConcertSchedules(1_000, 100);

        // then
    }

}
