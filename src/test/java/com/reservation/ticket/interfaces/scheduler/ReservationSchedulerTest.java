package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.enums.ReservationStatus;
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
public class ReservationSchedulerTest {

    @Autowired
    SchedulerManager schedulerManager;

    @Autowired
    ReservationService reservationService;

    @DisplayName("5분이 지났을때 결제가 이루어지지 않은 10개의 예약씩 취소하며, 점유한 좌석도 점유 이전의 상태로 된다.")
    @Test
    public void given_when_then() {
        // given
        int cancelledReservationCount = 0;
        List<Reservation> reservations = reservationService.selectReservationsByReservationStatus(ReservationStatus.CANCELLED);
        assertThat(reservations.size()).isEqualTo(cancelledReservationCount);

        // when
        schedulerManager.activateReservationScheduler();

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
