package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.command.SeatCommand;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;
import com.reservation.ticket.domain.entity.concert.reservation.SeatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 예약 통합 테스트
 */
@SpringBootTest
class ReservationUsecaseTest {

    @Autowired ReservationUsecase reservationUsecase;
    @Autowired SeatService seatService;

    @DisplayName("예약정보가 주어지면 예약이 이루어지며, 예약한 좌석이 점유된다.")
    @Test
    void givenReservationData_whenMakeReservation_thenNothingReturn() {
        // given
        int price = 4000;
        Long concertScheduleId = 1L;
        List<Long> seats = List.of(5L, 8L);
        String token = "734488355d85";
        ReservationCommand.Create create = ReservationCommand.Create.of(concertScheduleId, seats, price);

        // when
        ReservationCommand.Get reservation = reservationUsecase.makeReservation(create, token);

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
