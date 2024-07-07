package com.reservation.ticket.controller;

import com.reservation.ticket.controller.dto.concert.ConcertResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @GetMapping
    public ResponseEntity<List<ConcertResponse>> selectConcerts() {
        List<ConcertResponse> concerts = List.of(
                        ConcertResponse.of(1L, "concert1"),
                        ConcertResponse.of(2L, "concert2")
        );
        return ResponseEntity.ok().body(concerts);
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertResponse> selectConcertById(@PathVariable Long concertId) {
        ConcertResponse concert = ConcertResponse.of(concertId, "concert1");
        return ResponseEntity.ok().body(concert);
    }

}
