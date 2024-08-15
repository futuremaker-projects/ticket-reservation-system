package com.reservation.ticket.concurrency;

import com.reservation.ticket.application.dto.criteria.ReservationCriteria;
import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.entity.concert.reservation.ReservationRepository;
import com.reservation.ticket.domain.entity.concert.reservation.ReservationService;
import com.reservation.ticket.domain.entity.concert.reservation.ticket.Ticket;
import com.reservation.ticket.domain.entity.concert.reservation.ticket.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationConcurrencyTest {

    @Autowired
    ReservationUsecase sut;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;

    @DisplayName("[동시성 테스트 - Lock 미적용] 좌석 2개를 선택후 콘서트 예약시 하나 이상의 예약이 성공한다.")
    @Test
    public void test01() throws InterruptedException {
        // given
        Long concertScheduleId = 1L;
        List<Long> seatIds = List.of(1L, 2L);
        int price = 100;
        ReservationCriteria criteria = ReservationCriteria.of(concertScheduleId, seatIds, price);

        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            // 선택한 좌석을 서로 차지하도록 해야 한다.
            executorService.submit(() -> {
                try {
                    sut.makeReservationSendingMessage(criteria, getToken());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        // then
        List<Reservation> reservations = reservationRepository.findAll();

        /**
         * 서로 다른 예약이 성공하며 2개의 좌석은 2개 티켓보다 그 이상으로 많이 발행된다.
         */
        assertThat(reservations.size()).isEqualTo(50);
    }

    @DisplayName("[동시성 테스트 - 비관적락 적용] 좌석 2개를 선택후 콘서트 예약시 하나의 예약만 성공한다.")
    @Test
    public void test02() throws InterruptedException {
        // given
        Long concertScheduleId = 4L;
        List<Long> seatIds = List.of(4L, 5L);
        int price = 100;
        ReservationCriteria criteria = ReservationCriteria.of(concertScheduleId, seatIds, price);

        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            // 선택한 좌석을 서로 차지하도록 해야 한다.
            executorService.submit(() -> {
                try {
                    sut.makeReservationWithPessimisticLock(criteria, getToken());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        // then
        List<Ticket> tickets = ticketRepository.getSeats(concertScheduleId, seatIds);

        /**
         * 총 2개의 좌석이 점유되야 한다.
         */
        assertThat(tickets.size()).isEqualTo(2);
    }

    @DisplayName("[동시성 테스트 - 분산락 적용]좌석 2개를 선택후 콘서트 예약시 하나의 예약만 성공한다.] ")
    @Test
    void test03() throws InterruptedException {
        // given
        Long concertScheduleId = 4L;
        List<Long> seatIds = List.of(4L, 5L);
        int price = 100;
        ReservationCriteria criteria = ReservationCriteria.of(concertScheduleId, seatIds, price);

        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            // 선택한 좌석을 서로 차지하도록 해야 한다.
            executorService.submit(() -> {
                try {
                    sut.makeReservationWithDistributedLock(criteria, getToken());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        // then
        List<Ticket> tickets = ticketRepository.getSeats(concertScheduleId, seatIds);

        /**
         * 총 2개의 좌석이 점유되야 한다.
         */
        assertThat(tickets.size()).isEqualTo(2);
    }

    /**
     * 메서드 호출시 토큰을 랜덤하게 뽑아오도록 만든다. (ACTIVE 상태값인 토큰들)
     */
    private String getToken() {
        Random random = new Random();
        int randomNumber = random.nextInt(30); // nextInt(100)은 0부터 99까지의 숫자를 반환
        List<String> tokens = List.of(
                "734488355d85",
                "b02567dca468",
                "66b40f8df234",
                "2ff449c014be",
                "4fb8cf64e7fc",
                "9b8c1b3b43d6",
                "053ce98d889e",
                "8c7ea614a541",
                "1f504eb92b17",
                "5faa5b0e096a",
                "f0821578376e",
                "a2fad0ab0b91",
                "6de0beb8dd9c",
                "a8a35ed32dc6",
                "90035b74abd2",
                "69de36dd13da",
                "f34c40bf862c",
                "f5a66d909e82",
                "3cc3706dde50",
                "9217614788bc",
                "1bcb268fae04",
                "de96614d48ff",
                "f8690b657706",
                "88a04915f20f",
                "5415bd9063f3",
                "e78fa290fa55",
                "66377694ad39",
                "69327559bd0e",
                "167eb2329e46",
                "a7c9a66556c7"
        );

        return tokens.get(randomNumber);
    }

}
