package com.reservation.ticket.controller;

import com.reservation.ticket.controller.dto.queue.TokenStatus;
import com.reservation.ticket.controller.dto.queue.QueueResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @PostMapping("/token")
    public ResponseEntity<QueueResponseDto> createToken() {
        QueueResponseDto queue = QueueResponseDto.of(1L, 1L, "UUID-1", TokenStatus.WAIT,
                LocalDateTime.of(2022, 5, 20, 2, 10),
                LocalDateTime.of(2022, 5, 20, 2, 10));

        return ResponseEntity.ok().body(queue);
    }

}
