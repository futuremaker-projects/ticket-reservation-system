package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.entity.Payment;
import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.repository.PaymentRepository;
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
