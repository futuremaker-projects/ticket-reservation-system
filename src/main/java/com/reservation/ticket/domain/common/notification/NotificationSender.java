package com.reservation.ticket.domain.common.notification;

public interface NotificationSender {

    void send(Notification.Send send);

}
