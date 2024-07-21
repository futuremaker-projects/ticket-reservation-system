package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;
import com.reservation.ticket.domain.repository.ReservationRepository;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     *  예약 id를 이용하여 예약 정보를 가져온다.
     */
    public ReservationCommand.Get getReservationById(Long reservationId) {
        return ReservationCommand.Get.from(reservationRepository.findById(reservationId));
    }

    public ReservationCommand.Get save(int price, UserAccount userAccount) {
        Reservation reservation = Reservation.of(userAccount, price);
        return ReservationCommand.Get.from(reservationRepository.save(reservation));
    }

    /**
     * 예약된 상위 10개의 목록을 조회하여 결재상태가 NOT_PAID 이며, 현재시간보다 5분 초과된 상태면
     * `ACTIVE`(예약중) 인 상태를 `CANCELLED`(취소) 로 변경한다.
     */
    public List<Long> changeReservationStatusIfNotPaidOnTime() {
        int limit = 10;
        List<Long> cancelledIds = new ArrayList<>();
        List<Reservation> reservations =
                reservationRepository.findAllByReservationStatusOrderByIdAsc(ReservationStatus.ACTIVE, limit);
        reservations.forEach(reservation -> {
            if (reservation.getPaymentStatus() == PaymentStatus.NOT_PAID
                    && reservation.getReservedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                reservation.changeReservationStatus(ReservationStatus.CANCELLED);
                cancelledIds.add(reservation.getId());
            }
        });

        return cancelledIds;
    }

    public List<Reservation> selectReservationsByReservationStatus(ReservationStatus reservationStatus) {
        return reservationRepository.findAllByReservationStatus(reservationStatus);
    }

    public Reservation changePaymentStatusAsPaid(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        reservation.changePaymentStatus(PaymentStatus.PAID);
        return reservation;
    }
}

