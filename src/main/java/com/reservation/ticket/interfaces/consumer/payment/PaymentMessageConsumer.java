package com.reservation.ticket.interfaces.consumer.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.common.dataPlatform.DataPlatformClient;
import com.reservation.ticket.domain.common.notification.Notification;
import com.reservation.ticket.domain.common.notification.NotificationSender;
import com.reservation.ticket.domain.common.outbox.OutboxMessageWriter;
import com.reservation.ticket.domain.entity.concert.reservation.payment.message.PaymentMessage;
import com.reservation.ticket.infrastructure.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentMessageConsumer {

    private final DataPlatformClient dataPlatformClient;
    private final NotificationSender notificationSender;

    private final OutboxMessageWriter outboxMessageWriter;

    private final ObjectMapper objectMapper;

    /**
     * outbox 검증 로직
     * */
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "group1")
    public void complete(KafkaMessage<?> message) {
        outboxMessageWriter.complete(message.getId());
    }

    /**
     * 데이터 플랫폼 호출 (외부 API)
     * */
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "group2")
    public void sendData(KafkaMessage<?> message) {
        LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) message.getMessage();
        PaymentMessage.Get get = objectMapper.convertValue(map, PaymentMessage.Get.class);
        callDataPlatform(get);
    }

    /**
     * slack 알림 연동
     * */
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "group3")
    public void sendNotification(KafkaMessage<?> message) {
        LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) message.getMessage();
        PaymentMessage.Get get = objectMapper.convertValue(map, PaymentMessage.Get.class);
        sendNotification(get);
    }

    @Retryable(
            maxAttempts = 2,                    // 최대 시도 횟수
            backoff = @Backoff(delay = 1000)    // 재시도 간격
    )
    private void callDataPlatform(PaymentMessage.Get get) {
        boolean result = dataPlatformClient.send(get.paymentId());
        if (!result) {
            log.warn("data platform send failed");
        }
    }

    private void sendNotification(PaymentMessage.Get get) {
        Notification.Send send = Notification.Send.of(get.paymentId().toString());
        notificationSender.send(send);
    }

}
