package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.command.SeatCommand;
import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;
import com.reservation.ticket.domain.service.ReservationService;
import com.reservation.ticket.domain.service.SeatService;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 예약 통합 테스트
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationUsecaseTest {

    @Autowired
    ReservationUsecase reservationUsecase;
    @Autowired
    ReservationService reservationService;
    @Autowired
    SeatService seatService;

    @DisplayName("5분동안 결제가 이루어지지 않은 10개의 예약씩 취소하며, 점유한 좌석도 점유 이전의 상태로 된다.")
    @Test
    void given_when_then() {
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

    @DisplayName("예약정보가 주어지면 예약이 이루어지며, 예약한 좌석이 점유된다.")
    @Test
    void givenReservationData_whenMakeReservation_thenNothingReturn() {
        // given
        int price = 4000;
        Long concertScheduleId = 1L;
        List<Long> seats = List.of(1L, 2L, 5L, 8L);
        Long userId = 1L;
        ReservationCommand.Create create = ReservationCommand.Create.of(concertScheduleId, seats, userId, price);

        // when
        ReservationCommand.Get reservation = reservationUsecase.makeReservation(create);

        // then
        assertThat(reservation).isNotNull();
        assertThat(reservation.paymentStatus()).isEqualTo(PaymentStatus.NOT_PAID);
        assertThat(reservation.reservationStatus()).isEqualTo(ReservationStatus.ACTIVE);

        // 저장된 좌석을 찾아 검증한다.
        List<SeatCommand.Get> findSeats = seatService.selectSeatsByIds(seats);
        assertThat(findSeats).isNotEmpty();
        assertThat(findSeats.size()).isEqualTo(seats.size());
    }

}
