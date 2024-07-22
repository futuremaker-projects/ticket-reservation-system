package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.entity.complex.ReservationSeat;
import com.reservation.ticket.domain.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationUsecase {

    private final ReservationService reservationService;
    private final QueueService queueService;
    private final SeatService seatService;
    private final UserAccountService userAccountService;

    private final ReservationSeatService reservationSeatService;

    @Transactional
    public ReservationCommand.Get makeReservation(ReservationCommand.Create create, String token) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 예약을 진행한다.
        ReservationCommand.Get reservation = reservationService.save(create.price(), userAccount);
        // 예약시 선택한 자리를 점유한다.
        reservationSeatService.save(reservation.id(), create.concertScheduleId(), create.seatIds());
//        seatService.changeSeatOccupiedStatus(reservation.id(), create.seatIds());

        queueService.renewQueueExpirationDate(token);
        return reservation;
    }

    @Transactional
    public void releaseOccupiedSeats() {
        /**
         *  `ACTIVE` 상태인 상위 10개의 예약을 가져와 미결재이며 현재시간과 비교하여 5분차이 시
         *  상태값을 `CANCELLED` 로 변경
         */
        List<Long> cancelledReservationIds = reservationService.changeReservationStatusIfNotPaidOnTime();
        /**
         *  예약으로 선점된 좌석을 다시 원상복구 한다.
         */


//        seatService.recoverSeatOccupiedStatus(cancelledReservationIds);
    }
}

