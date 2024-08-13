package com.reservation.ticket.domain.entity.point.event;

import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.entity.concert.reservation.payment.Payment;
import com.reservation.ticket.domain.entity.concert.reservation.payment.event.PaymentEvent;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RecordApplicationEvents
@SpringBootTest
class PointEventListenerTest {

    @Autowired
    ApplicationEvents applicationEvents;

    @MockBean
    PointEventListener pointEventListener;

    @DisplayName("이벤트를 이용하여 예약금액을 포인트에서 차감한다.")
    @Test
    void pointEventTest() {
        // given
        Long userId = 1L;
        int userPoint = 1000;
        int price = 100;
        Long reservationId = 1L;
        Long paymentId = 1L;

        UserAccount userAccount = UserAccount.of(userId, userPoint);
        Reservation reservation = Reservation.of(reservationId, userAccount, price);
        Payment payment = Payment.of(paymentId, userAccount, reservation);
        PaymentEvent.Success success = PaymentEvent.Success.of(reservation, userAccount, payment);

        // when
        pointEventListener.usePointEvent(success);

        // then
        assertThat(applicationEvents.stream(PointEvent.Use.class))
                .hasSize(1)
                .anySatisfy(event -> {
                    assertAll(
                            () -> assertThat(event.userId()).isNotNull(),
                            () -> assertThat(event.reservationId()).isNotNull(),
                            () -> assertThat(event.paymentId()).isNotNull()
                    );
                });
    }

}