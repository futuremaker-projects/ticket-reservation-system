package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;
import com.reservation.ticket.domain.repository.ReservationRepository;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationService sut;

    @Mock ReservationRepository reservationRepository;
    @Mock UserAccountRepository userAccountRepository;

    /**
     * 사용자 id, 예약금액으로 예약을 저장한다.
     *    - 결제 상태와 예약 상태는 각각 `NOT_PAID(미결제)`, `ACTIVE(예약중)` 상태로 저장됨 (Default)
     */
    @Test
    void givenUserIdAndPrice_whenRequestingSaveReservation_thenSavesReservation() {
        // given
        Long userId = 1L;
        UserAccount userAccount = UserAccount.of(userId);
        given(userAccountRepository.findById(userId)).willReturn(userAccount);

        int price = 1000;
        Reservation reservation = Reservation.of(userAccount, price, PaymentStatus.NOT_PAID, ReservationStatus.ACTIVE);
        given(reservationRepository.save(any(Reservation.class))).willReturn(reservation);

        // when
        ReservationCommand.Get savedReservation = sut.save(price, userId);

        // then
        assertThat(savedReservation.price()).isEqualTo(reservation.getPrice());
        assertThat(savedReservation.paymentStatus()).isEqualTo(reservation.getPaymentStatus());
        assertThat(savedReservation.reservationStatus()).isEqualTo(reservation.getReservationStatus());
    }

    @DisplayName("에약 id로 예약을 조회하여 결재상태를 `PAID`로 변경한다.")
    @Test
    public void givenReservationId_whenChangingPaymentStatusAsPaid_thenChangingPaymentStatusAsPaid() {
        // given
        Long reservationId = 1L;
        Long userId = 1L;
        Reservation reservation = Reservation.of(reservationId, UserAccount.of(userId), 1000);
        given(reservationRepository.findById(reservationId)).willReturn(reservation);

        // when
        Reservation changedReservation = sut.changePaymentStatusAsPaid(reservationId);

        // then
        assertThat(changedReservation.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
    }

}