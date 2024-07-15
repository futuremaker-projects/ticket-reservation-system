package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.entity.PointHistory;
import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.TransactionType;
import com.reservation.ticket.domain.repository.PointHistoryRepository;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointHistoryRepository pointHistoryRepository;

    public void usePoint(Reservation reservation, UserAccount userAccount) {
        if (reservation.getPrice() > userAccount.getPoint()) {
            throw new ApplicationException(ErrorCode.NOT_ENOUGH_POINT,
                    "not enough point for price - point : %d".formatted(userAccount.getPoint()));
        }

        // 사용자 포인트 삭감
        int restPoint = userAccount.getPoint() - reservation.getPrice();
        userAccount.savePoint(restPoint);

        // 포인트 히스토리 저장
        PointHistory pointHistory = PointHistory.of(userAccount, TransactionType.USE);
        pointHistoryRepository.save(pointHistory);
    }
}
