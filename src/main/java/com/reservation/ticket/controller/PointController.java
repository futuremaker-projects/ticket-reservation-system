package com.reservation.ticket.controller;

import com.reservation.ticket.controller.dto.point.PointResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
public class PointController {

    @GetMapping
    public ResponseEntity<PointResponseDto> getPoint() {
        /**
         *  사용자의 id가 필요하다. 포인트는 사용자의 컬럼에 들어갈지 아니면 테이블로 빼낼지 고민해야한다.
         */
        Long userId = 1L;
        PointResponseDto pointResponseDto = PointResponseDto.of(1L, 1000);
        return ResponseEntity.ok().body(pointResponseDto);
    }

}
