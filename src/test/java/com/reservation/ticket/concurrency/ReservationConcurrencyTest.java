package com.reservation.ticket.concurrency;

import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.domain.command.ReservationCommand;
import com.reservation.ticket.domain.entity.complex.Ticket;
import com.reservation.ticket.domain.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReservationConcurrencyTest {

    @Autowired ReservationUsecase sut;
    @Autowired
    TicketRepository ticketRepository;

    @DisplayName("락을 적용하지 않고 예약후 좌석을 선점하는 기능 테스트")
    @Test
    public void test01() throws InterruptedException {
        // given
        Long concertScheduleId = 4L;
        List<Long> seatIds = List.of(4L, 5L);
        int price = 100;
        ReservationCommand.Create create = ReservationCommand.Create.of(concertScheduleId, seatIds, price);

        int threadCount = 600;
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            // 선택한 좌석을 서로 차지하도록 해야 한다.
            executorService.submit(() -> {
                try {
                    sut.makeReservation(create, getToken());
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
         * 예약과 예약자리 테이블이 서로 맞는지도 확인해야 함
         *
         * 예약 7개
         * 예약자리 - 14개
         */

        /**
         * 총 2개의 좌석이 점유되야 한다.
         */
        assertThat(tickets.size()).isEqualTo(2);
    }

    @DisplayName("비관적락 적용후 예약후 좌석점유 기능 테스트")
    @Test
    public void test02() throws InterruptedException {
        // given
        Long concertScheduleId = 4L;
        List<Long> seatIds = List.of(4L, 5L);
        int price = 100;
        ReservationCommand.Create create = ReservationCommand.Create.of(concertScheduleId, seatIds, price);

        int threadCount = 600;
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            // 선택한 좌석을 서로 차지하도록 해야 한다.
            executorService.submit(() -> {
                try {
                    sut.makeReservationWithPessimisticLock(create, getToken());
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
     * 메서드 호출시 토큰을 랜덤하게 뽑아오도록 만든다.
     */
    private String getToken() {
        Random random = new Random();
        int randomNumber = random.nextInt(100); // nextInt(100)은 0부터 99까지의 숫자를 반환
        List<String> tokens = List.of(
                "6f8f504681f9", "b02567dca468", "66b40f8df234", "2ff449c014be", "4fb8cf64e7fc", "9b8c1b3b43d6",
                "053ce98d889e", "8c7ea614a541", "1f504eb92b17", "5faa5b0e096a", "f0821578376e", "a2fad0ab0b91",
                "6de0beb8dd9c", "a8a35ed32dc6", "90035b74abd2", "69de36dd13da", "f34c40bf862c", "f5a66d909e82",
                "3cc3706dde50", "9217614788bc", "1bcb268fae04", "ef8e9620d7b2", "de96614d48ff", "f8690b657706",
                "88a04915f20f", "5415bd9063f3", "e78fa290fa55", "66377694ad39", "69327559bd0e", "167eb2329e46",
                "a7c9a66556c7", "27c4c82ba7c3", "8e97d8e2280c", "a519618f9962", "bebce5c62653", "ae0170f435fb",
                "42a4e901866d", "acf6ab7b5bfa", "44c01892ccff", "6c298b22205e", "7511fd9b3332", "3b619bab34d8",
                "db4b4bf439eb", "b4491cd3d505", "2f4dc3ffcbca", "a9426d7e1826", "d8f832093007", "40a6ac3d7ba5",
                "feac01e34c0f", "0baa592f0912", "014868c92bd6", "bfe7d77b3791", "f92a02d60b45", "d46fd77a8038",
                "6a93532b0b06", "83d569984c8f", "bcfbad5a9f1f", "c3c941cdd722", "7337f71d73fe", "04c28320b32d",
                "b878013f8869", "3a3d8ef71ced", "3edb880d5f75", "9cba60bae156", "6588aab3a9d4", "6cb7eefdb52a",
                "4432f3c7dbbb", "2364ec536d0a", "883808c07736", "6d06f4b53e54", "0d9a82093a73", "3e52354cd206",
                "3c468c0ee8a2", "044bfc2df953", "853d638f91f3", "6353929387fc", "3420f839e38d", "f921b648853a",
                "99a122eb6888", "0a12a6b693e6", "d7c838d9c255", "ed9aeb9f3314", "5fa89c6dce27", "3462dcb33d86",
                "ceba2bb8446a", "0ece66fcd075", "24d99dc3110d", "e47d45523fd9", "a21a1b17f8d1", "b8d915cfc573",
                "2dce82c4c3d4", "c8e1684f644e", "5bb0e07bbc7e", "58630817085e", "3ad105d9d972", "bc7b7352387a",
                "61de1348970b", "ef81caca55ae", "86dc14042c80", "734488355d85"
        );

        return tokens.get(randomNumber);
    }

}
