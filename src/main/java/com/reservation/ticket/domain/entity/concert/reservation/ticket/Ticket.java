package com.reservation.ticket.domain.entity.concert.reservation.ticket;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(of = {"createdAt", "id"})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {

    @EmbeddedId
    private TicketComplexIds id;

    private LocalDateTime createdAt;

    @PrePersist
    public void createdAt() {
        createdAt = LocalDateTime.now();
    }

    public Ticket(TicketComplexIds id) {
        this.id = id;
    }

    public static Ticket of(TicketComplexIds id) {
        return new Ticket(id);
    }

}
