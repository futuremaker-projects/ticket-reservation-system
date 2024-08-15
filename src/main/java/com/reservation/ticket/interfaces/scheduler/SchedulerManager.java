package com.reservation.ticket.interfaces.scheduler;

import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.domain.common.outbox.OutboxMessageWriter;
import com.reservation.ticket.domain.entity.queue.QueueRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerManager {

    private final QueueRedisService queueRedisService;
    private final ReservationUsecase reservationUsecase;
    private final OutboxMessageWriter outboxMessageWriter;

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

    @Scheduled(cron = "5 * * * * *", zone = "Asia/Seoul")
    public void resendMessage() {
        outboxMessageWriter.resend();
    }

}
