package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.entity.complex.Ticket;
import com.reservation.ticket.domain.entity.complex.TicketComplexIds;
import com.reservation.ticket.domain.enums.LockType;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;
import com.reservation.ticket.domain.repository.ReservationRepository;
import com.reservation.ticket.domain.repository.TicketRepository;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final RedissonClient redissonClient;

    /**
     *  예약 id를 이용하여 예약 정보를 가져온다.
     */
    public ReservationCommand.Get getReservationById(Long reservationId) {
        return ReservationCommand.Get.from(reservationRepository.findById(reservationId));
    }

    public ReservationCommand.Get reserve(ReservationCommand.Create create, UserAccount userAccount, LockType lockType) {
        Reservation reservation = Reservation.of(userAccount, create.price());
        Reservation savedReservation = reservationRepository.save(reservation);

        // 좌석의 점유상태를 검증한다.
        switch (lockType) {
            case NONE -> checkIfSeatsAvailable(create.concertScheduleId(), create.seatIds());
            case PESSIMISTIC_READ -> checkIfSeatsAvailableWithPessimisticLock(create.concertScheduleId(), create.seatIds());
        }

        // 예약시 선택한 자리를 점유한다.
        create.seatIds().forEach(seatId -> {
            Ticket ticket = Ticket.of(
                    new TicketComplexIds(create.concertScheduleId(), seatId, savedReservation.getId()));
            ticketRepository.save(ticket);
        });
        return ReservationCommand.Get.from(savedReservation);
    }

    public List<Reservation> selectReservationsByReservationStatus(ReservationStatus reservationStatus) {
        return reservationRepository.findAllByReservationStatus(reservationStatus);
    }

    public Reservation changePaymentStatusAsPaid(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        reservation.changePaymentStatus(PaymentStatus.PAID);
        return reservation;
    }

    public void checkIfSeatsAvailable(Long concertScheduleId, List<Long> seatIds) {
        List<Ticket> tickets = ticketRepository.getSeats(concertScheduleId, seatIds);
        checkSeats(tickets);
    }

    public void checkIfSeatsAvailableWithPessimisticLock(Long concertScheduleId, List<Long> seatIds) {
        List<Ticket> tickets = ticketRepository.getSeatsWithPessimisticLock(concertScheduleId, seatIds);
        checkSeats(tickets);
    }

    public void checkSeats(List<Ticket> tickets) {
        if (!tickets.isEmpty()) {
            throw new ApplicationException(ErrorCode.SEAT_ALREADY_OCCUPIED, "seat already occupied : %s".formatted(tickets));
        }
    }


    /**
     * 1. 예약된 상위 10개의 목록을 조회하여 결재상태가 NOT_PAID 이며, 현재시간보다 5분 초과된 상태면
     * `ACTIVE`(예약중) 인 상태를 `CANCELLED`(취소) 로 변경한다.
     * 2. 예약으로 선점된 좌석을 다시 원상복구 한다.
     */
    @Transactional
    public void cancelReservation() {
        int limit = 10;
        List<Long> cancelledIds = new ArrayList<>();
        List<Reservation> reservations =
                reservationRepository.findAllByReservationStatusOrderByIdAsc(ReservationStatus.ACTIVE, limit);
        reservations.forEach(reservation -> {
            if (reservation.getPaymentStatus() == PaymentStatus.NOT_PAID
                    && reservation.getReservedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                reservation.changeReservationStatus(ReservationStatus.CANCELLED);
                cancelledIds.add(reservation.getId());
            }
        });
        releaseSeats(cancelledIds);
    }

    public void releaseSeats(List<Long> reservationIds) {
        ticketRepository.removeSeats(reservationIds);
    }
}

