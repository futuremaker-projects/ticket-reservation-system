package com.reservation.ticket.domain.entity.userAccount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccount getUserAccountById(Long userId) {
        return userAccountRepository.findById(userId);
    }

    public UserAccount getUserAccountByToken(String token) {
        return userAccountRepository.findByToken(token);
    }
}
