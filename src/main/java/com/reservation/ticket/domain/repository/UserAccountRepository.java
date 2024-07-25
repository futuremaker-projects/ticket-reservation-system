package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.UserAccount;

public interface UserAccountRepository {

    UserAccount findById(Long id);

    UserAccount findByToken(String token);

    UserAccount getUserAccount(String token);

    UserAccount save(UserAccount userAccount);
}
