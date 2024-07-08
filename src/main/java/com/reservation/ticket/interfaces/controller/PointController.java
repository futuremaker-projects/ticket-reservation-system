package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.interfaces.controller.dto.point.PointRequest;
import com.reservation.ticket.interfaces.controller.dto.point.PointResponse;
import com.reservation.ticket.interfaces.controller.dto.userAccount.UserAccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/point")
public class PointController {

    /**
     *  사용자 id를 이용하여 사용자가 가진 포인트를 조회한다.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<PointResponse> getPoint(@PathVariable Long userId) {

        UserAccountResponse userAccountResponse = UserAccountResponse.of(userId, "name", 1000);
        PointResponse pointResponse = PointResponse.of(userAccountResponse.point());
        return ResponseEntity.ok().body(pointResponse);
    }

    /**
     * 사용자 id와 충전할 포인트를 전달받아 포인트를 충전한다.
     */
    @PostMapping
    public ResponseEntity<Void> chargePoint(@RequestBody PointRequest requestDto) {
        return ResponseEntity.ok().build();
    }



}
