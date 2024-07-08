package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.domain.service.ConcertService;
import com.reservation.ticket.interfaces.controller.dto.concert.ConcertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping
    public ResponseEntity<List<ConcertDto.Response>> selectConcerts() {
        List<ConcertDto.Response> responses = concertService.selectAllConcerts().stream()
                .map(ConcertDto.Response::from)
                .toList();

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<ConcertDto.Response> selectConcertById(@PathVariable Long concertId) {
        return ResponseEntity.ok().body(ConcertDto.Response.of(1L, "concert1"));
    }

}
