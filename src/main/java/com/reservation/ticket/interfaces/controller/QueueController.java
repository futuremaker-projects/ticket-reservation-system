package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.domain.entity.queue.QueueService;
import com.reservation.ticket.interfaces.dto.QueueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Void> createToken(@RequestBody QueueDto.Request request) {
        String token = queueService.createQueue(request.userId());

        // 여기서 토큰을 해더에 담아줘야한다.
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }

}

