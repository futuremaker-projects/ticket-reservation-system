package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.service.QueueService;
import com.reservation.ticket.domain.service.ReservationService;
import com.reservation.ticket.domain.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationUsecase {

    private final ReservationService reservationService;
    private final QueueService queueService;
    private final SeatService seatService;

    @Transactional
    public ReservationCommand.Get makeReservation(ReservationCommand.Create create, Long userId) {
        QueueCommand.Get queue = queueService.verifyQueueByUserId(userId);
        // 예약을 진행한다.
        ReservationCommand.Get reservation = reservationService.save(create, userId);
        // 예약시 선택한 자리를 점유한다.
        seatService.changeSeatOccupiedStatus(reservation.id(), create.seatIds());
        queueService.renewQueueExpirationDate(queue.token());
        return reservation;
    }

    @Transactional
    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void releaseOccupiedSeats() {
        /**
         *  `ACTIVE` 상태인 상위 10개의 예약을 가져와 미결재이며 현재시간과 비교하여 5분차이 시
         *  상태값을 `CANCELLED` 로 변경
         */
        List<Long> cancelledReservationIds = reservationService.changeReservationStatusWhenNotPaidOnTime();
        /**
         *  예약으로 선점된 좌석을 다시 원상복구 한다.
         */
        seatService.recoverSeatOccupiedStatus(cancelledReservationIds);
    }
}

