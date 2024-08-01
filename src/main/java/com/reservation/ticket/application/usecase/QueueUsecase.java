package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.entity.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueUsecase {

    private final QueueService queueService;

    public void changeStatusToExpire() {
        queueService.changeTokenStatusToExpire();
    }


}
