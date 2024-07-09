package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.repository.QueueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueueRepositoryImplTest {

    @Autowired
    private QueueRepository repository;

    @Test
    void wowo() {
        // given
        int i = repository.countByStatus(QueueStatus.ACTIVE);
        System.out.println("i = " + i);

        // when

        // then
    }

}