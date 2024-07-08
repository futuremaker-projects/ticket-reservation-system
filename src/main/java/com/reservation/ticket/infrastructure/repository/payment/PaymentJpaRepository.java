package com.reservation.ticket.infrastructure.repository.payment;

import com.reservation.ticket.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
