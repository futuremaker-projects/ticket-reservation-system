package com.reservation.ticket.controller;

import com.reservation.ticket.controller.dto.point.PointRequestDto;
import com.reservation.ticket.controller.dto.point.PointResponseDto;
import com.reservation.ticket.controller.dto.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
public class PointController {

    /**
     *  사용자 id를 이용하여 사용자가 가진 포인트를 조회한다.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<PointResponseDto> getPoint(@PathVariable Long userId) {

        User user = User.of(userId, "name", 1000);
        PointResponseDto pointResponseDto = PointResponseDto.of(user.point());
        return ResponseEntity.ok().body(pointResponseDto);
    }

    /**
     * 사용자 id와 충전할 포인트를 전달받아 포인트를 충전한다.
     */
    @PostMapping
    public ResponseEntity<Void> chargePoint(@RequestBody PointRequestDto requestDto) {
        return ResponseEntity.ok().build();
    }



}
