package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.PointCommand;
import com.reservation.ticket.domain.entity.PointHistory;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.TransactionType;
import com.reservation.ticket.domain.repository.PointHistoryRepository;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
import com.reservation.ticket.interfaces.controller.dto.point.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserAccountRepository userAccountRepository;
    private final PointHistoryRepository pointHistoryRepository;

    /**
     * 포인트 조회
     */
    public PointCommand.Get getPoint(Long userId) {
        UserAccount user = userAccountRepository.findById(userId);
        return PointCommand.Get.of(user.getPoint());
    }

    /**
     * 포인트 사용 - 포인트가 결제할 금액보다 작을 경우 예외발생
     */
    public void usePoint(int reservedPrice, UserAccount userAccount) {
        if (reservedPrice > userAccount.getPoint()) {
            throw new ApplicationException(ErrorCode.NOT_ENOUGH_POINT,
                    "not enough point for price - point : %d".formatted(userAccount.getPoint()));
        }

        // 사용자 포인트 삭감
        int restPoint = userAccount.getPoint() - reservedPrice;
        userAccount.savePoint(restPoint);

        // 포인트 히스토리 저장
        PointHistory pointHistory = PointHistory.of(userAccount, TransactionType.USE, restPoint);
        pointHistoryRepository.save(pointHistory);
    }

    /**
     * 포인트 충전 - 포인트가 0이거나 0이하면 예외 발생
     */
    public void chargePoint(PointCommand.Update request) {
        if (request.point() <= 0) {
            throw new ApplicationException(ErrorCode.INVALID_POINT,
                    "Invalid chargeable point : %d".formatted(request.point()));
        }
        UserAccount userAccount = userAccountRepository.findById(request.userId());
        int chargeablePoint = userAccount.getPoint() + request.point();
        userAccount.savePoint(chargeablePoint);
    }
}
