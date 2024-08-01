package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.application.usecase.QueueUsecase;
import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.domain.entity.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerManager {

    private QueueService queueService;
    private final ReservationUsecase reservationUsecase;

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void activateQueueScheduler() {
        queueService.changeTokenStatusToActive();
    }

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void expireQueueScheduler() {
        queueService.changeTokenStatusToExpire();
    }

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void activateReservationScheduler() {
        reservationUsecase.cancelReservation();
    }

}
