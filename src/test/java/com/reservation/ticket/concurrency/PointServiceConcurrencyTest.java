package com.reservation.ticket.concurrency;

import com.reservation.ticket.domain.command.PointCommand;
import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.entity.complex.Ticket;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import com.reservation.ticket.domain.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
public class PointServiceConcurrencyTest {

    @Autowired
    PointService pointService;
    @Autowired
    UserAccountRepository userAccountRepository;

    @DisplayName("포인트를 연속 충전 동시성 이슈 테스트")
    @Test
    public void chargingPointOptimisticLockTest() throws InterruptedException {
        // given
        int chargeablePoint = 100;
        String token = "734488355d85";

        /**
         * 5번 포인트를 연속으로 충전시 최초의 한번의 포인트 충전 요청만 적용되도록 한다.
         */
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            // 선택한 좌석을 서로 차지하도록 해야 한다.
            executorService.submit(() -> {
                try {
                    pointService.chargePoint(chargeablePoint, token);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        PointCommand.Get point = pointService.getPoint(token);
        assertThat(point.point()).isEqualTo(10100);
    }

}
