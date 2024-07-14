package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.service.PaymentService;
import com.reservation.ticket.domain.service.QueueService;
import com.reservation.ticket.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentUsecase {

    /**
     결제는 포인트로 진행됨.

     예약을 가져와야 함. 어떻게? userId가 아니라 콘서트 스케줄 id 로 가져와야 하는데?
     스케줄이 아니라 생성된 예약 id를 이용하여 가져온다. 예약은 유저의 종속적이 될거 같다.
     1. 사용자의 예약 리스트보기,
     2. 예약후 결제페이지에서 예약 정보 보기
     최초에는 userId로 내 예약 검색이 되야하고,
     예약이 완료된 후 바로 예매를 한다면 결제 페이지로 이동을 하겠지
     결제 페이지로 이동 후, 포인트가 부족하다면 포인트 충전 및 결제 진행이 이루어 진다.

     - 결제가 성공적으로 진행이 됬다면 아래와 같은 플로우

     1. 대기열 토큰 검증 및 연장
     2. 결제 생성
     3, 예약의 결제 상태값을 PAID로 변경 필요

     */

    private final QueueService queueService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;

    @Transactional
    public void makePaymentForReservationDone(Long reservationId, Long userId) {
        // 대기열 토큰 검증
        queueService.renewExpirationDate(userId);
        // 예약의 결제 상태값을 PAID로 변경
        Reservation reservation = reservationService.changePaymentStatusAsPaid(reservationId);
        // 결재 생성 -> 포인트 차감 -> 잔액부족이면 예외처리
        paymentService.makePayment(reservation, UserAccount.of(userId));
    }

}
