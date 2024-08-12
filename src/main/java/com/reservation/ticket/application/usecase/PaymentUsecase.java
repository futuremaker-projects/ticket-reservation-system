package com.reservation.ticket.application.usecase;

import com.reservation.ticket.application.dto.criteria.PaymentCriteria;
import com.reservation.ticket.domain.common.dataPlatform.DataPlatformClient;
import com.reservation.ticket.domain.dto.command.PaymentCommand;
import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.entity.concert.reservation.ReservationService;
import com.reservation.ticket.domain.entity.concert.reservation.payment.PaymentService;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.entity.userAccount.UserAccountService;
import com.reservation.ticket.infrastructure.repository.reservation.ReservationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentUsecase {

    private final PaymentService paymentService;
    private final UserAccountService userAccountService;
    private final ReservationService reservationService;
    private final ReservationRepositoryImpl reservationRepositoryImpl;

    public void makePayment(PaymentCriteria.Create create) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(create.token());
        // 예약 이벤트로 변경해야 함
        Reservation reservation = reservationService.getReservation(create.reservationId());
        // 예약완료를 위한 결제정보 등록

        PaymentCommand.Create command = PaymentCommand.Create.of(reservation, userAccount);
        paymentService.createPayment(command);
    }

}
