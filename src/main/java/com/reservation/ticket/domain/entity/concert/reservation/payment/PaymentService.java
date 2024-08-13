package com.reservation.ticket.domain.entity.concert.reservation.payment;

import com.reservation.ticket.domain.dto.command.PaymentCommand;
import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.entity.concert.reservation.ReservationService;
import com.reservation.ticket.domain.entity.concert.reservation.payment.event.PaymentEvent;
import com.reservation.ticket.domain.entity.concert.reservation.payment.event.PaymentEventPublisher;
import com.reservation.ticket.domain.entity.point.event.PointEvent;
import com.reservation.ticket.domain.entity.queue.QueueRedisService;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.entity.userAccount.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    @Transactional
    public void createPayment(PaymentCommand.Create create) {
        Payment payment = Payment.of(create.userAccount(), create.reservation());
        Payment savedPayment = paymentRepository.save(payment);

        /**
         *  결재성공 이벤트 발행
         *  - 예약금액 만큼의 포인트 차감
         *  - 대기열 토큰 만료 - redis의 Active 대기열에서 삭제
         */
        eventPublisher.publishSuccess(
                PaymentEvent.Success.of(create.reservation(), create.userAccount(), savedPayment)
        );
    }

    public void deletePayment(Long paymentId) {
        paymentRepository.delete(paymentId);
    }

}
