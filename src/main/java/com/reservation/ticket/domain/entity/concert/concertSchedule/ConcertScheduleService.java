package com.reservation.ticket.domain.entity.concert.concertSchedule;

import com.reservation.ticket.domain.dto.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.dto.info.ConcertScheduleInfo;
import com.reservation.ticket.infrastructure.dto.entity.ConcertScheduleEntity;
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
    public List<ConcertScheduleInfo> selectAllConcertSchedulesByConcertId(Long concertId) {
        return concertScheduleRepository.findAllByConcertId(concertId).stream()
                .map(ConcertScheduleInfo::from)
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

    public List<ConcertScheduleEntity> getConcertSchedule(Long scheduleId) {
        return concertScheduleRepository.getConcertSchedule(scheduleId);
    }

}
