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
@ToString(of = {"concertScheduleId", "seatId"})
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleSeatComplexIds implements Serializable {

    @Column(name = "concert_schedule_id")
    private Long concertScheduleId;
    @Column(name = "seat_id")
    private Long seatId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof ScheduleSeatComplexIds ids)) return false;
        return Objects.equals(concertScheduleId, ids.concertScheduleId) && Objects.equals(seatId, ids.seatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(concertScheduleId, seatId);
    }




}
