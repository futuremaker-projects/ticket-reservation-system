package com.reservation.ticket.domain.entity.concert.reservation.event;

public interface ReservationEventPublisher {

    void successReservation(ReservationEvent.Success success);

}
