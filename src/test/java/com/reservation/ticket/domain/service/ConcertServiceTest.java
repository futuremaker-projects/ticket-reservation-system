package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.repository.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @InjectMocks
    ConcertService concertService;

    @Mock
    ConcertRepository concertRepository;

    @Test
    void givenNothing_whenRequesting_thenReturnsConcertList() {
        // given
        Long concertId = 1L;


        // when

        // then
    }

}