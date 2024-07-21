package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.application.usecase.QueueUsecase;
import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.domain.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerManager {

    private final QueueUsecase queueUsecase;
    private final ReservationUsecase reservationUsecase;

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void activateQueueScheduler() {
        queueUsecase.changeTokenStatus();
    }

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void activateReservationScheduler() {
        reservationUsecase.releaseOccupiedSeats();
    }

}
