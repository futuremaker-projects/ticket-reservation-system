package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentUsecase {

    private final UserAccountService userAccountService;
    private final QueueService queueService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final PointService pointService;

    @Transactional
    public void makePayment(Long reservationId, String token) {
        // 대기열 토큰 검증 및 만료
        queueService.expireQueue(token);
        // 예약의 결제 상태값을 PAID로 변경
        Reservation reservation = reservationService.changePaymentStatusAsPaid(reservationId);
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 포인트 차감 -> 잔액부족이면 예외처리
        pointService.usePoint(reservation.getPrice(), userAccount);
        // 결재 생성
        paymentService.createPayment(reservation, userAccount);
    }

}
