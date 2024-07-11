package com.reservation.ticket.domain.entity.complex;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleSeat {

    @EmbeddedId
    private ScheduleSeatComplexIds id;

    private Long reservationId;
    private LocalDateTime createdAt;

    @PrePersist
    public void createdAt() {
        createdAt = LocalDateTime.now();
    }

    public ScheduleSeat(ScheduleSeatComplexIds id, Long reservationId) {
        this.id = id;
        this.reservationId = reservationId;
    }

    public static ScheduleSeat of(ScheduleSeatComplexIds id, Long reservationId) {
        return new ScheduleSeat(id, reservationId);
    }

}
