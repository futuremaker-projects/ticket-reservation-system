package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.domain.entity.queue.QueueRedisService;
import com.reservation.ticket.domain.entity.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerManager {

    private final QueueRedisService queueRedisService;
    private final ReservationUsecase reservationUsecase;

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void activateQueueScheduler() {
        queueRedisService.changeTokenStatusToActive();
    }

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void expireQueueScheduler() {
        queueRedisService.changeTokenStatusToExpire();
    }

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void activateReservationScheduler() {
        reservationUsecase.cancelReservation();
    }

}
