package com.reservation.ticket.domain.entity.userAccount;

public interface UserAccountRepository {

    UserAccount findById(Long id);

    UserAccount findByToken(String token);

    UserAccount getUserAccount(String token);

    UserAccount save(UserAccount userAccount);
}
