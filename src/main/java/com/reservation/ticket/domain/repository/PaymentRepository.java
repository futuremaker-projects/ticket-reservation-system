package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);

}
