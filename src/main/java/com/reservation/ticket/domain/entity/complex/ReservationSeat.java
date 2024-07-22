package com.reservation.ticket.domain.entity.complex;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(of = {"reservationId", "createdAt"})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationSeat {

    @EmbeddedId
    private ReservationSeatComplexIds id;

    private Long reservationId;
    private LocalDateTime createdAt;

    @PrePersist
    public void createdAt() {
        createdAt = LocalDateTime.now();
    }

    public ReservationSeat(ReservationSeatComplexIds id, Long reservationId) {
        this.id = id;
        this.reservationId = reservationId;
    }

    public static ReservationSeat of(ReservationSeatComplexIds id, Long reservationId) {
        return new ReservationSeat(id, reservationId);
    }

}
