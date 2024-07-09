package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.UserAccount;

import java.util.Optional;

public interface UserAccountRepository {

    UserAccount findById(Long id);

}
