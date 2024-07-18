package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.entity.ConcertSchedule;
import com.reservation.ticket.domain.repository.ConcertScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertScheduleService {

    private final ConcertScheduleRepository concertScheduleRepository;

    /**
     *  콘서트 id로 전체 콘서트 스케줄을 불러온다.
     */
    public List<ConcertScheduleCommand.Get> selectAllConcertSchedulesByConcertId(Long concertId) {
        return concertScheduleRepository.findAllByConcertId(concertId).stream()
                .map(ConcertScheduleCommand.Get::from)
                .toList();
    }

    /**
     * 콘서트 스케줄 id로 스케줄 단건조회
     */
    public ConcertSchedule getConcertScheduleById(Long concertScheduleId) {
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(concertScheduleId);
        return concertSchedule;
    }

    /**
     *  선택된 날짜의 콘서트 스케줄 단건조회
     */
    public ConcertScheduleCommand.Get getConcertScheduleByOpenedDate(LocalDateTime openedDate) {
        return ConcertScheduleCommand.Get.from(concertScheduleRepository.findByOpenedAt(openedDate));
    }

    /**
     * 콘서트 스케줄에 등록된 자리를 가져온다.
     *  자리는 1에서 50자리이다. 자리는 스케줄이 등록시 미리 등록되어야 한다.
     */



}
