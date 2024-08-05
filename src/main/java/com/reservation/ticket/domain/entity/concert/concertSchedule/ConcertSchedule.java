package com.reservation.ticket.domain.entity.concert.concertSchedule;

import com.reservation.ticket.domain.entity.concert.Concert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@ToString(of = {"id", "limitSeat", "openedAt"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int limitSeat;
    private LocalDateTime openedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Concert concert;

    public ConcertSchedule(Long id, int limitSeat, LocalDateTime openedAt, Concert concert) {
        this.id = id;
        this.concert = concert;
        this.limitSeat = limitSeat;
        this.openedAt = openedAt;
    }

    public static ConcertSchedule of(Long id, int limitSeat, LocalDateTime openedAt, Concert concert) {
        return new ConcertSchedule(id, limitSeat, openedAt, concert);
    }

    public static ConcertSchedule of(Long id, int limitSeat, LocalDateTime openedAt) {
        return new ConcertSchedule(id, limitSeat, openedAt, null);
    }

    public static ConcertSchedule of(int limitSeat, LocalDateTime openedAt, Concert concert) {
        return new ConcertSchedule(null, limitSeat, openedAt, concert);
    }

    public static ConcertSchedule of(int limitSeat, LocalDateTime openedAt) {
        return new ConcertSchedule(null, limitSeat, openedAt, null);
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
