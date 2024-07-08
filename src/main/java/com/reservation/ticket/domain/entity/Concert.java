package com.reservation.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ConcertSchedule> concertSchedules = new ArrayList<>();

    public Concert(Long id, String name, LocalDateTime createdAt, List<ConcertSchedule> concertSchedules) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.concertSchedules = concertSchedules;
    }

    public static Concert of(String name, LocalDateTime createdAt) {
        return new Concert(null, name, createdAt, null);
    }

    public static Concert of(String name, LocalDateTime createdAt, List<ConcertSchedule> concertSchedules) {
        return new Concert(null, name, createdAt, concertSchedules);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Concert concert)) return false;
        return id != null && id.equals(concert.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
