package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueUsecase {

    private final QueueService queueService;

    public void changeTokenStatus() {
        queueService.changeTokenStatusExpiredOrActive();
    }

}
