package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.service.*;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationUsecase {

    private final ReservationService reservationService;
    private final QueueService queueService;
    private final UserAccountService userAccountService;

    private final TicketService ticketService;

    @Transactional
    public ReservationCommand.Get makeReservation(ReservationCommand.Create create, String token) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 예약을 진행한다.
        ReservationCommand.Get reservation = reservationService.save(create.price(), userAccount);
        // 예약시 선택한 자리를 점유한다.
        ticketService.save(reservation.id(), create.concertScheduleId(), create.seatIds(), LockModeType.NONE);
        // 대기열 토큰의 만료일을 연장한다.
        queueService.renewQueueExpirationDate(token);
        return reservation;
    }

    @Transactional
    public ReservationCommand.Get makeReservationWithPessimisticLock(ReservationCommand.Create create, String token) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 예약을 진행한다.
        ReservationCommand.Get reservation = reservationService.save(create.price(), userAccount);
        // 예약시 선택한 자리를 점유한다.
        ticketService.save(reservation.id(), create.concertScheduleId(), create.seatIds(), LockModeType.PESSIMISTIC_READ);
        // 대기열 토큰의 만료일을 연장한다.
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
        ticketService.releaseSeats(cancelledReservationIds);
    }
}

