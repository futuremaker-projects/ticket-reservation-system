package com.reservation.ticket.infrastructure.repository.pointHistory;

import com.reservation.ticket.domain.entity.point.pointhistory.PointHistory;
import com.reservation.ticket.domain.entity.point.pointhistory.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return pointHistoryJpaRepository.save(pointHistory);
    }
}
