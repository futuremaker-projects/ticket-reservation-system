package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.interfaces.controller.dto.queue.TokenStatus;
import com.reservation.ticket.interfaces.controller.dto.queue.QueueResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/queue")
public class QueueController {

    @PostMapping("/token")
    public ResponseEntity<QueueResponse> createToken() {
        QueueResponse queue = QueueResponse.of(1L, 1L, "UUID-1", TokenStatus.WAIT,
                LocalDateTime.of(2022, 5, 20, 2, 10),
                LocalDateTime.of(2022, 5, 20, 2, 10));

        return ResponseEntity.ok().body(queue);
    }

}
