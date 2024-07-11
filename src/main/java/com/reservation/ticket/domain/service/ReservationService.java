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

    @Transactional
    public ReservationCommand.Get save(ReservationCommand.Create create, Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId);
        Reservation reservation = Reservation.of(userAccount, create.price());
        return ReservationCommand.Get.from(reservationRepository.save(reservation));
    }

    @Transactional
    public List<Long> changeReservationStatusWhenNotPaidOnTime() {
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

}
