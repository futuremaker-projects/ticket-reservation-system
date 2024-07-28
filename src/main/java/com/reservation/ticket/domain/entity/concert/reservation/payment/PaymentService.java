package com.reservation.ticket.domain.entity.concert.reservation.payment;

import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void createPayment(Reservation reservation, UserAccount userAccount) {
        Payment payment = Payment.of(userAccount, reservation);
        paymentRepository.save(payment);
    }
}
