package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.entity.queue.QueueService;
import com.reservation.ticket.interfaces.controller.dto.QueueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    /**
     * 사용자의 정보를 이용하여 최초 대기열 데이터 생성한다.
     */
    @PostMapping("/token")
    public ResponseEntity<QueueDto.Response> createToken(@RequestBody QueueDto.Request request) {
        QueueCommand.Get queue = queueService.createQueue(request.userId());
        return ResponseEntity.ok().body(queue.toResponse());
    }

}

