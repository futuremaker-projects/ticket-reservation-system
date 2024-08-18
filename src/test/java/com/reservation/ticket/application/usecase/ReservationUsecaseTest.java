package com.reservation.ticket.application.usecase;

import com.reservation.ticket.application.dto.criteria.ReservationCriteria;
import com.reservation.ticket.application.dto.result.ReservationResult;
import com.reservation.ticket.domain.dto.command.SeatCommand;
import com.reservation.ticket.domain.dto.info.ReservationInfo;
import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.entity.concert.reservation.ReservationRepository;
import com.reservation.ticket.domain.entity.concert.reservation.SeatService;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;
import com.reservation.ticket.infrastructure.dto.statement.OutboxStatement;
import com.reservation.ticket.infrastructure.repository.common.OutboxRepositoryImpl;
import com.reservation.ticket.infrastructure.repository.reservation.ReservationRepositoryImpl;
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

    @Autowired ReservationUsecase sut;

    @Autowired SeatService seatService;
    @Autowired ReservationRepositoryImpl reservationRepository;
    @Autowired OutboxRepositoryImpl outboxRepository;

    @DisplayName("예약정보가 주어지면 예약이 이루어지며, 선택한 좌석이 점유된다.")
    @Test
    void givenReservationData_whenMakeReservation_thenNothingReturn() {
        // given
        int price = 4000;
        Long concertScheduleId = 1L;
        List<Long> seats = List.of(5L, 8L);
        String token = "734488355d85";
        ReservationCriteria criteria = ReservationCriteria.of(concertScheduleId, seats, price);

        // when
        ReservationResult reservation = sut.makeReservation(criteria, token);

        // then
        assertThat(reservation).isNotNull();
        assertThat(reservation.paymentStatus()).isEqualTo(PaymentStatus.NOT_PAID);
        assertThat(reservation.reservationStatus()).isEqualTo(ReservationStatus.ACTIVE);

        // 저장된 좌석을 찾아 검증한다.
        List<SeatCommand.Get> findSeats = seatService.selectSeatsByIds(seats);
        assertThat(findSeats).isNotEmpty();
        assertThat(findSeats.size()).isEqualTo(seats.size());
    }

    @DisplayName("예약하고 좌석 점유를 위해 메시지를 보내고 outbox 저장")
    @Test
    void test02() {
        // given
        int price = 4000;
        Long concertScheduleId = 1L;
        List<Long> seats = List.of(5L, 8L);
        String token = "734488355d85";
        ReservationCriteria criteria = ReservationCriteria.of(concertScheduleId, seats, price);

        // when
        ReservationInfo reservationInfo = sut.makeReservationSendingMessage(criteria, token);

        // then
        Reservation reservation = reservationRepository.findById(reservationInfo.id());
        assertThat(reservation).isNotNull();
        assertThat(reservation.getPrice()).isEqualTo(price);
    }

}
