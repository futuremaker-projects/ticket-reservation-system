package com.reservation.ticket.domain.entity.complex;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(of = {"createdAt"})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationSeat {

    @EmbeddedId
    private ReservationSeatComplexIds id;

    private LocalDateTime createdAt;

    @Version
    private Long version;

    @PrePersist
    public void createdAt() {
        createdAt = LocalDateTime.now();
    }

    public ReservationSeat(ReservationSeatComplexIds id) {
        this.id = id;
    }

    public static ReservationSeat of(ReservationSeatComplexIds id) {
        return new ReservationSeat(id);
    }

}
