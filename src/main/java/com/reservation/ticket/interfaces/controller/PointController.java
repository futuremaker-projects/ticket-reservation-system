package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.domain.command.PointCommand;
import com.reservation.ticket.domain.service.PointService;
import com.reservation.ticket.interfaces.controller.dto.point.PointDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    /**
     *  토큰을 이용하여 사용자가 가진 포인트를 조회한다.
     */
    @GetMapping
    public ResponseEntity<PointDto.Response> getPoint(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        PointDto.Response response = PointDto.Response.from(pointService.getPoint(token));
        return ResponseEntity.ok().body(response);
    }

    /**
     * 토큰과 충전할 포인트를 전달받아 포인트를 충전한다.
     */
    @PostMapping
    public ResponseEntity<Void> chargePoint(
            @RequestBody PointDto.Request requestDto, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        pointService.chargePoint(requestDto.point(), token);
        return ResponseEntity.ok().build();
    }

}

