package com.reservation.ticket.domain.entity;

import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
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

    public Seat(Long id, int seatNumber, Long reservationId, Long concertScheduleId, boolean occupied, LocalDateTime occupiedAt) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.reservationId = reservationId;
        this.concertScheduleId = concertScheduleId;
        this.occupied = occupied;
        this.occupiedAt = occupiedAt;
    }

    public static Seat of(Long id) {
        return new Seat(id, 0, null, null, false, null);
    }

    public void changeToOccupiedAndSaveReservationId(Long reservationId) {
        if (this.reservationId != null) {
            throw new ApplicationException(ErrorCode.CONFLICT, "Seat already occupied : %d".formatted(this.id));
        }
        this.reservationId = reservationId;
        this.occupied = true;
        this.occupiedAt = LocalDateTime.now();
    }

    public void releaseOccupiedSeat() {
        this.reservationId = null;
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
