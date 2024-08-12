package com.reservation.ticket.domain.service;

import com.reservation.ticket.application.dto.criteria.PaymentCriteria;
import com.reservation.ticket.domain.dto.command.PaymentCommand;
import com.reservation.ticket.domain.entity.concert.reservation.payment.Payment;
import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.entity.concert.reservation.payment.PaymentService;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.entity.concert.reservation.payment.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentService sut;

    @Mock PaymentRepository paymentRepository;

    @DisplayName("예약과 사용자를 이용하여 결제를 등록한다.")
    @Test
    public void givenReservationAndUserAccount_whenSavesPayment_thenSavesPayment() {
        // given
        Long userId = 1L;
        Long reservationId = 1L;
        Long paymentId = 1L;
        String token = "734488355d85";
        UserAccount userAccount = UserAccount.of(userId);
        Reservation reservation = Reservation.of(reservationId);

        LocalDateTime paidAt = LocalDateTime.of(2022, 5, 10, 2, 10);
        Payment savedPayment = Payment.of(paymentId, userAccount, reservation, paidAt);

        given(paymentRepository.save(any(Payment.class))).willReturn(savedPayment);

        // when
        sut.createPayment(PaymentCommand.Create.of(reservation, userAccount));

        // then
        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        then(paymentRepository).should().save(paymentCaptor.capture());

        Payment capturedPayment = paymentCaptor.getValue();
        assertThat(capturedPayment).isNotNull();
        assertThat(capturedPayment.getUserAccount()).isEqualTo(userAccount);
        assertThat(capturedPayment.getReservation()).isEqualTo(reservation);
    }
}