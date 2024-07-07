package com.reservation.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertSchedule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Concert concert;

    private int limitSeat;

    private LocalDateTime openedAt;

    public ConcertSchedule(Long id, Concert concert, int limitSeat, LocalDateTime openedAt) {
        this.id = id;
        this.concert = concert;
        this.limitSeat = limitSeat;
        this.openedAt = openedAt;
    }

    public static ConcertSchedule of(Concert concert, int limitSeat, LocalDateTime openedAt) {
        return new ConcertSchedule(null, concert, limitSeat, openedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConcertSchedule concertSchedule)) return false;
        return id != null && id.equals(concertSchedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
