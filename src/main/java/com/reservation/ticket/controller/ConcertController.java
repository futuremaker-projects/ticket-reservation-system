package com.reservation.ticket.controller;

import com.reservation.ticket.controller.dto.concert.ConcertResponseDto;
import com.reservation.ticket.controller.dto.concertInfo.ConcertInfoResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @GetMapping
    public ResponseEntity<List<ConcertResponseDto>> selectConcerts() {

        List<ConcertResponseDto> concerts = List.of(
                ConcertResponseDto.of(1L, 10, LocalDateTime.of(2022, 5, 20, 2, 10),
                        ConcertInfoResponseDto.of(1L, "concert1")),
                ConcertResponseDto.of(2L, 10, LocalDateTime.of(2022, 5, 20, 2, 10),
                        ConcertInfoResponseDto.of(2L, "concert2"))
        );

        return ResponseEntity.ok().body(concerts);
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertResponseDto> selectConcertById(@PathVariable Long concertId) {
        ConcertResponseDto concert =
                ConcertResponseDto.of(1L, 10, LocalDateTime.of(2022, 5, 20, 2, 10),
                        ConcertInfoResponseDto.of(1L, "concert1"));
        return ResponseEntity.ok().body(concert);
    }

}
