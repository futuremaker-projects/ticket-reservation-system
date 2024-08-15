package com.reservation.ticket.application.usecase;

import com.reservation.ticket.application.dto.criteria.ReservationCriteria;
import com.reservation.ticket.application.dto.result.ReservationResult;
import com.reservation.ticket.domain.dto.info.ReservationInfo;
import com.reservation.ticket.domain.entity.concert.reservation.ReservationService;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.entity.userAccount.UserAccountService;
import com.reservation.ticket.domain.enums.LockType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationUsecase {

    private final ReservationService reservationService;
    private final UserAccountService userAccountService;

    public ReservationResult makeReservation(ReservationCriteria criteria, String token) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 예약을 진행한다.
        ReservationInfo reservationInfo = reservationService.reserve(criteria.toCreate(), userAccount, LockType.NONE);
        return ReservationResult.from(reservationInfo);
    }

    public ReservationInfo makeReservationSendingMessage(ReservationCriteria criteria, String token) {
        reservationService.checkIfSeatsAvailable(criteria.concertScheduleId(), criteria.seatIds());
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        ReservationInfo reservationInfo = reservationService.reserve(criteria.toCreate(), userAccount);
        return reservationInfo;
    }

    public ReservationResult makeReservationWithPessimisticLock(ReservationCriteria criteria, String token) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 예약을 진행한다. (비관적락)
        ReservationInfo reservation = reservationService.reserve(criteria.toCreate(), userAccount, LockType.PESSIMISTIC_WRITE);
        return ReservationResult.from(reservation);
    }

    public ReservationResult makeReservationWithDistributedLock(ReservationCriteria criteria, String token) {
        UserAccount userAccount = userAccountService.getUserAccountByToken(token);
        // 예약을 진행한다. (분산락)
        ReservationInfo reservation = reservationService.reserve(criteria.toCreate(), userAccount, LockType.DISTRIBUTED_LOCK);
        return ReservationResult.from(reservation);
    }

    public void cancelReservation() {
        reservationService.cancelReservation();
    }

}

