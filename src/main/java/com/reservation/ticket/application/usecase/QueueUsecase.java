package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.entity.queue.QueueServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueUsecase {

    private final QueueServiceImpl queueService;

    public void changeTokenStatus() {
        queueService.changeTokenStatusExpiredOrActive();
    }

}
