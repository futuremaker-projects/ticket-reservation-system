package com.reservation.ticket.domain.entity.complex;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class ScheduleSeat {

    @EmbeddedId
    private ScheduleSeatComplexIds id;

    private Long reservationId;

    private LocalDateTime createdAt;

}
