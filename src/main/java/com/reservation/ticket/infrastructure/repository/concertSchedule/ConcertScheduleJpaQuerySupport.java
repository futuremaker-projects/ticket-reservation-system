package com.reservation.ticket.infrastructure.repository.concertSchedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reservation.ticket.domain.entity.concert.concertSchedule.ConcertSchedule;
import com.reservation.ticket.infrastructure.dto.entity.ConcertScheduleEntity;
import com.reservation.ticket.infrastructure.dto.entity.QConcertScheduleEntity;
import com.reservation.ticket.infrastructure.dto.entity.QSeatEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.reservation.ticket.domain.entity.concert.concertSchedule.QConcertSchedule.concertSchedule;
import static com.reservation.ticket.domain.entity.concert.reservation.QSeat.seat;

@Repository
public class ConcertScheduleJpaQuerySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ConcertScheduleJpaQuerySupport(JPAQueryFactory queryFactory) {
        super(ConcertSchedule.class);
        this.queryFactory = queryFactory;
    }

    public List<ConcertScheduleEntity> getConcertSchedulesById(Long id) {
        Map<ConcertSchedule, ConcertScheduleEntity> result = queryFactory.from(concertSchedule)
                .leftJoin(seat).on(concertSchedule.id.eq(seat.concertScheduleId))
                .where(concertSchedule.id.eq(id))
                .transform(groupBy(concertSchedule).as(new QConcertScheduleEntity(
                        concertSchedule.id, concertSchedule.limitSeat,
                        list(new QSeatEntity(
                                seat.id, seat.concertScheduleId,
                                seat.occupied, seat.occupiedAt
                        ))
                )));

        List<ConcertScheduleEntity> list = result.keySet().stream().map(result::get).toList();
        return list;
    }


}
