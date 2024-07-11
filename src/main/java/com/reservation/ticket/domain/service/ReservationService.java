package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.repository.ReservationRepository;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserAccountRepository userAccountRepository;

    public ReservationCommand.Get save(ReservationCommand.Create create, Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId);
        Reservation reservation = Reservation.of(userAccount, create.price());
        return ReservationCommand.Get.from(reservationRepository.save(reservation));
    }



}
