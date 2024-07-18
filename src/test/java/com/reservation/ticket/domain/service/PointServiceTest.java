package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.PointCommand;
import com.reservation.ticket.domain.entity.PointHistory;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.TransactionType;
import com.reservation.ticket.domain.repository.PointHistoryRepository;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import com.reservation.ticket.utils.DummyData;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @InjectMocks PointService sut;

    @Mock PointHistoryRepository pointHistoryRepository;
    @Mock UserAccountRepository userAccountRepository;

    /**
     * 포인트 사용
     */
    @DisplayName("예약_금액과_사용자의_포인트를_이용하여_사용자_포인트를_차감한다.")
    @Test
    public void 예약_금액과_사용자의_포인트를_이용하여_사용자_포인트를_차감한다() {
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
    @DisplayName("사용자 id와 충전할 포인트를 이용하여 포인트를 충전하다.")
    @Test
    void 사용자id와_충전할_포인트를_이용하여_포인트를_충전하다() {
        // given
        Long userId = 1L;
        int chargeablePoint = 1000;
        String token = "734488355d85";
        PointCommand.Update update = PointCommand.Update.of(userId, chargeablePoint);

        int userPoint = 100;
        UserAccount userAccount = UserAccount.of(userId, "noah", token, userPoint);
        given(userAccountRepository.findByToken(token)).willReturn(userAccount);

        // when
        sut.chargePoint(chargeablePoint, token);

        // then
        assertThat(userAccount.getPoint()).isEqualTo(chargeablePoint + userPoint);
    }

    /**
     * 포인트 충전시 예외 - 충전할 포인트가 `0`일때
     */
    @Test
    void 충전할_포인트가_0이면_예외를_발생한다() {
        // given
        Long userId = 1L;
        int chargeablePoint = 0;
        String token = "734488355d85";

        // when
        Throwable t = catchThrowable(() -> sut.chargePoint(chargeablePoint, token));

        // then
        assertThat(t)
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Invalid chargeable point : %d".formatted(chargeablePoint));
    }

    /**
     * 포인트 충전 예외 - 포인트 충전시 예외 - 충전할 포인트가 `0`이하일떄
     */
    @Test
    void 충전할_포인트가_0이하면_예외를_발생한다() {
        // given
        int chargeablePoint = -100;
        String token = "734488355d85";

        // when
        Throwable t = catchThrowable(() -> sut.chargePoint(chargeablePoint, token));

        // then
        assertThat(t)
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Invalid chargeable point : %d".formatted(chargeablePoint));
    }

    /**
     * 포인트 조회
     */
    @Test
    void 사용자의_포인트를_조회한다() {
        // given
        Long userId = 1L;
        int userPoint = 100;
        String token = "734488355d85";

        UserAccount userAccount = UserAccount.of(userId, "noah", token, userPoint);
        given(userAccountRepository.findById(userId)).willReturn(userAccount);

        // when
        PointCommand.Get point = sut.getPoint(token);

        // then
        assertThat(point).isNotNull();
        assertThat(point.point()).isEqualTo(userPoint);
    }
}