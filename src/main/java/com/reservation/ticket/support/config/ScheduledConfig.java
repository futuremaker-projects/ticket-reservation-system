package com.reservation.ticket.support.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("com.reservation.ticket.interfaces.scheduler")
public class ScheduledConfig {
}
