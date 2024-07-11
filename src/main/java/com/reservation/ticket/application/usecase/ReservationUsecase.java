package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.command.ScheduleSeatCommand;
import com.reservation.ticket.domain.service.ReservationService;
import com.reservation.ticket.domain.service.ScheduleSeatService;
import com.reservation.ticket.domain.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationUsecase {

    private final ReservationService reservationService;
    private final ScheduleSeatService scheduleSeatService;
    private final SeatService seatService;

    @Transactional
    public void makeReservation(ReservationCommand.Create create, Long userId) {
        ReservationCommand.Get reservation = reservationService.save(create, userId);
        scheduleSeatService.save(reservation.id(), create.concertId(), create.seatIds());
        seatService.changeSeatOccupiedStatus(create.seatIds());
    }

}
