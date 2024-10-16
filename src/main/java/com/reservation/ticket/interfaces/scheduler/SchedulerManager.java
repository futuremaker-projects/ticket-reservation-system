package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.domain.entity.queue.QueueRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerManager {

    private static int limit = 3000;

    private final QueueRedisService queueRedisService;
    private final ReservationUsecase reservationUsecase;

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void activateQueueScheduler() {
        queueRedisService.createActiveQueue(limit);
        log.info("active queue created done");
    }

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void activateReservationScheduler() {
        reservationUsecase.cancelReservation();
    }

}
