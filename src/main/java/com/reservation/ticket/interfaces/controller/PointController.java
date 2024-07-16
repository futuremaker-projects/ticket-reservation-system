package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.domain.service.PointService;
import com.reservation.ticket.interfaces.controller.dto.point.PointDto;
import com.reservation.ticket.interfaces.controller.dto.userAccount.UserAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    /**
     *  사용자 id를 이용하여 사용자가 가진 포인트를 조회한다.
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<PointDto.Response> getPoint(@PathVariable Long userId) {
        UserAccountResponse userAccountResponse = UserAccountResponse.of(userId, "name", 1000);
        PointDto.Response pointResponse = PointDto.Response.of(userAccountResponse.point());
        return ResponseEntity.ok().body(pointResponse);
    }

    /**
     * 사용자 id와 충전할 포인트를 전달받아 포인트를 충전한다.
     */
    @PostMapping
    public ResponseEntity<Void> chargePoint(@RequestBody PointDto.Request requestDto) {
        pointService.chargePoint(requestDto.toPointCommandUpdate());
        return ResponseEntity.ok().build();
    }

}
