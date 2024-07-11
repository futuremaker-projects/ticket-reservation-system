package com.reservation.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@ToString(of = {"id", "reservationId", "concertScheduleId", "seatNumber", "occupiedAt", "occupied"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seatNumber;
    private LocalDateTime occupiedAt;
    private boolean occupied;

    private Long reservationId;
    private Long concertScheduleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat seat)) return false;
        return id != null && id.equals(seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void changeToOccupiedAndSaveReservationId(Long reservationId) {
        this.reservationId = reservationId;
        this.occupied = true;
        this.occupiedAt = LocalDateTime.now();
    }

    public void releaseOccupiedSeat() {
        this.reservationId = null;
        this.occupied = false;
        this.occupiedAt = null;
    }
}
