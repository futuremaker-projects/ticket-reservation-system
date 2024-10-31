package com.reservation.ticket.domain.entity.concert.reservation.payment.event;

import org.springframework.stereotype.Component;

@Component
public interface PaymentEventPublisher {

    void publishSuccess(PaymentEvent.Success success);


}
