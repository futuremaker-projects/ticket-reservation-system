package com.reservation.ticket.domain.entity.concert.reservation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@ToString(of = {"id", "concertScheduleId", "occupiedAt", "occupied"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime occupiedAt;
    private boolean occupied;

    private Long concertScheduleId;

    public Seat(Long id, Long concertScheduleId, boolean occupied, LocalDateTime occupiedAt) {
        this.id = id;
        this.concertScheduleId = concertScheduleId;
        this.occupied = occupied;
        this.occupiedAt = occupiedAt;
    }

    public static Seat of(Long id) {
        return new Seat(id, null, false, null);
    }

    public static Seat of(Long id, Long concertScheduleId, boolean occupied, LocalDateTime occupiedAt) {
        return new Seat(id, concertScheduleId, occupied, occupiedAt);
    }

    public void changeToOccupiedAndSaveReservationId(Long reservationId) {
        this.occupied = true;
        this.occupiedAt = LocalDateTime.now();
    }

    public void releaseOccupiedSeat() {
        this.occupied = false;
        this.occupiedAt = null;
    }

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
}
