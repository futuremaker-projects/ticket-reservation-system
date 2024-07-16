package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.service.QueueService;
import com.reservation.ticket.interfaces.controller.dto.queue.QueueDto;
import com.reservation.ticket.interfaces.controller.dto.queue.TokenStatus;
import com.reservation.ticket.interfaces.controller.dto.queue.QueueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    /**
     * 대기열 생성
     */
    @PostMapping("/token")
    public ResponseEntity<QueueDto.Response> createToken(@RequestBody QueueDto.Request request) {
        QueueCommand.Get queue = queueService.createQueue(request.userId());
        return ResponseEntity.ok().body(queue.toResponse());
    }

}
