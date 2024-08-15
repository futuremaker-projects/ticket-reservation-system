package com.reservation.ticket.infrastructure.spring.reservation;

import com.reservation.ticket.domain.entity.concert.reservation.event.ReservationEvent;
import com.reservation.ticket.domain.entity.concert.reservation.event.ReservationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationSpringEventPublisher implements ReservationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void successReservation(ReservationEvent.Success success) {
        eventPublisher.publishEvent(success);
    }

}
