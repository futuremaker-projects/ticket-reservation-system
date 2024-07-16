package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.entity.Payment;
import com.reservation.ticket.domain.entity.PointHistory;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.TransactionType;
import com.reservation.ticket.domain.repository.PointHistoryRepository;
import com.reservation.ticket.dummy.DummyData;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @InjectMocks PointService sut;

    @Mock PointHistoryRepository pointHistoryRepository;

    /**
     * 포인트 사용
     */
    @DisplayName("예약 금액과 사용자의 포인트를 이용하여 사용자 포인트를 차감한다.")
    @Test
    public void givenReservationPriceAndUserPoint_whenCalculatePrice_then() {
        // given
        int reservedPrice = 1000;
        Long userId = 1L;
        int userPoint = 10000;
        UserAccount userAccount = UserAccount.of(userId, "noah", DummyData.generateToken(), userPoint);

        Long pointHistoryId = 1L;
        int restPoint = userPoint - reservedPrice;
        PointHistory pointHistory = PointHistory.of(pointHistoryId, userAccount, TransactionType.USE, restPoint);
        given(pointHistoryRepository.save(any(PointHistory.class))).willReturn(pointHistory);

        // when
        sut.usePoint(reservedPrice, userAccount);

        // then
        assertThat(userAccount.getPoint()).isEqualTo(restPoint);

        ArgumentCaptor<PointHistory> pointHistoryCaptor = ArgumentCaptor.forClass(PointHistory.class);
        then(pointHistoryRepository).should().save(pointHistoryCaptor.capture());

        PointHistory capturedPayment = pointHistoryCaptor.getValue();
        assertThat(capturedPayment).isNotNull();
        assertThat(capturedPayment.getUserAccount()).isEqualTo(userAccount);
        assertThat(capturedPayment.getTransactionType()).isEqualTo(TransactionType.USE);
        assertThat(capturedPayment.getPoint()).isEqualTo(restPoint);
    }

    /**
     * 포인트 사용시 포인트 부족하면 Exception 발생하도록
     */
    @DisplayName("사용자의_포인트가_예약금액_보다_적다면_예외를_발생한다")
    @Test
    public void 사용자의_포인트가_예약금액_보다_적다면_예외를_발생한다() {
        // given
        int reservedPrice = 1000;
        Long userId = 1L;
        int userPoint = 1;
        UserAccount userAccount = UserAccount.of(userId, "noah", DummyData.generateToken(), userPoint);

        // when
        Throwable t = catchThrowable(() -> sut.usePoint(reservedPrice, userAccount));

        // then
        assertThat(t)
                .isInstanceOf(ApplicationException.class)
                .hasMessage("not enough point for price - point : %d".formatted(userPoint));
    }

    /**
     * 포인트 충전
     */

    /**
     * 포인트 조회
     */

}