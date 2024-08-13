package com.reservation.ticket.domain.entity.concert.reservation.payment.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishSuccess(PaymentEvent.Success success) {
        publisher.publishEvent(success);
    }

}
