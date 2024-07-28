package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.enums.LockType;
import com.reservation.ticket.domain.entity.queue.QueueService;
import com.reservation.ticket.domain.entity.concert.reservation.ReservationService;
import com.reservation.ticket.domain.entity.userAccount.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationUsecase {

    private final ReservationService reservationService;
    private final QueueService queueService;
    private final UserAccountService userAccountService;

    @Transactional
    public ReservationCommand.Get makeReservation(ReservationCommand.Create create, String token) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 예약을 진행한다.
        ReservationCommand.Get reservation = reservationService.reserve(create, userAccount, LockType.NONE);
        // 대기열 토큰의 만료일을 연장한다.
        queueService.renewQueueExpirationDate(token);
        return reservation;
    }

    @Transactional
    public ReservationCommand.Get makeReservationWithPessimisticLock(ReservationCommand.Create create, String token) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 예약을 진행한다. (비관적락)
        ReservationCommand.Get reservation = reservationService.reserve(create, userAccount, LockType.PESSIMISTIC_READ);
        // 대기열 토큰의 만료일을 연장한다.
        queueService.renewQueueExpirationDate(token);
        return reservation;
    }

    @Transactional
    public ReservationCommand.Get makeReservationWithDistributedLock(ReservationCommand.Create create, String token) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 예약을 진행한다. (비관적락)
        ReservationCommand.Get reservation = reservationService.reserve(create, userAccount, LockType.PESSIMISTIC_READ);
        // 대기열 토큰의 만료일을 연장한다.
        queueService.renewQueueExpirationDate(token);
        return reservation;
    }

    public void cancelReservation() {
        reservationService.cancelReservation();
    }
}

