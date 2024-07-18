package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.application.usecase.PaymentUsecase;
import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;
import com.reservation.ticket.domain.service.QueueService;
import com.reservation.ticket.domain.service.ReservationService;
import com.reservation.ticket.infrastructure.config.ScheduledConfig;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({ScheduledConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SchedulerManagerTest {

    @Autowired SchedulerManager schedulerManager;

    @Autowired PaymentUsecase paymentUsecase;
    @Autowired ReservationUsecase reservationUsecase;

    @Autowired QueueService queueService;
    @Autowired ReservationService reservationService;

    @DisplayName("등록일을 기준으로 5분이 지났을 때 상태값이 `ACTIVE`면 `EXPIRED`로 변경시킨다.")
    @Test
    public void given_when_then() {
        // given
        int activeCount = 30;

        List<QueueCommand.Get> beforeGet = queueService.selectQueueByStatus(QueueStatus.ACTIVE);
        assertThat(beforeGet).isNotNull();
        assertThat(beforeGet.size()).isEqualTo(activeCount);

        // when
        schedulerManager.activateQueueScheduler();

        // then
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<QueueCommand.Get> gets = queueService.selectQueueByStatus(QueueStatus.ACTIVE);

                    int changeWaitToActiveCount = 15;
                    assertThat(gets).isNotNull();
                    assertThat(gets.size()).isEqualTo(changeWaitToActiveCount);
                });
    }

    @DisplayName("5분이 지났을때 결제가 이루어지지 않은 10개의 예약씩 취소하며, 점유한 좌석도 점유 이전의 상태로 된다.")
    @Test
    public void given_when_then2() {
        // given
        int cancelledReservationCount = 0;
        List<Reservation> reservations = reservationService.selectReservationsByReservationStatus(ReservationStatus.CANCELLED);
        assertThat(reservations.size()).isEqualTo(cancelledReservationCount);

        // when
        reservationUsecase.releaseOccupiedSeats();

        // then
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    int cancelledReservationCountScheduled = 10;
                    List<Reservation> cancelled = reservationService.selectReservationsByReservationStatus(ReservationStatus.CANCELLED);
                    assertThat(cancelled).isNotEmpty();
                    assertThat(cancelled.size()).isEqualTo(cancelledReservationCountScheduled);
                });
    }

}