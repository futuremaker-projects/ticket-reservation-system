package com.reservation.ticket.domain.entity.queue.event;

import com.reservation.ticket.domain.entity.concert.reservation.payment.event.PaymentEvent;
import com.reservation.ticket.domain.entity.queue.QueueRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class QueueEventListener {

    private final QueueRedisService queueRedisService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void paymentSuccessHandler(PaymentEvent.Success success) {
        queueRedisService.expire(success.userAccount().getToken());
    }

}
