package com.reservation.ticket.infrastructure.notification;

import com.reservation.ticket.domain.common.notification.Notification;
import com.reservation.ticket.domain.common.notification.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackNotificationSender implements NotificationSender {

    @Override
    public void send(Notification.Send send) {
        // Slack 알림
    }
}
