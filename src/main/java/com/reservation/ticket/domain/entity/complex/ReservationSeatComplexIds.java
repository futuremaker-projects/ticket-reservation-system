package com.reservation.ticket.domain.entity.complex;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@ToString(of = {"concertScheduleId", "seatId", "reservationId"})
@AllArgsConstructor
@NoArgsConstructor
public class ReservationSeatComplexIds implements Serializable {

    @Column(name = "concert_schedule_id")
    private Long concertScheduleId;
    @Column(name = "seat_id")
    private Long seatId;
    @Column(name = "reservation_id")
    private Long reservationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof ReservationSeatComplexIds ids)) return false;
        return Objects.equals(concertScheduleId, ids.concertScheduleId)
                && Objects.equals(seatId, ids.seatId)
                && Objects.equals(reservationId, ids.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(concertScheduleId, seatId, reservationId);
    }
}
