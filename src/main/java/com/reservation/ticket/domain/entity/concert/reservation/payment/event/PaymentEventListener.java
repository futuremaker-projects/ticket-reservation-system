package com.reservation.ticket.domain.entity.concert.reservation.payment.event;

import com.reservation.ticket.domain.common.dataPlatform.DataPlatformClient;
import com.reservation.ticket.domain.entity.concert.reservation.ReservationService;
import com.reservation.ticket.domain.entity.concert.reservation.payment.PaymentService;
import com.reservation.ticket.domain.entity.queue.QueueRedisService;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.QueueStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final QueueRedisService queueRedisService;

    private final DataPlatformClient dataPlatformClient;

    // TODO - Async 구현 필요, config 하자
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void paymentSuccessHandler(PaymentEvent.Success success) {
        callDataPlatform(success.reservation().getId());
    }

    @EventListener
    public void recoverFailure(PaymentEvent.Recover event) {
        reservationService.changePaymentStatus(event.reservationId(), PaymentStatus.NOT_PAID);
        paymentService.deletePayment(event.paymentId());
        queueRedisService.recoverQueue(event.userId(), QueueStatus.ACTIVE);
    }

    @Retryable(
            maxAttempts = 2,                    // 최대 시도 횟수
            backoff = @Backoff(delay = 1000)    // 재시도 간격
    )
    private void callDataPlatform(Long reservationId) {
        boolean result = dataPlatformClient.send(reservationId);
        if (!result) {
            log.warn("data platform send failed");
        }
    }

}
