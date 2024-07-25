package com.reservation.ticket.infrastructure.repository.userAccount;

import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.repository.UserAccountRepository;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserAccountRepositoryImpl implements UserAccountRepository {

    private final UserAccountJpaRepository userAccountJpaRepository;

    @Override
    public UserAccount findById(Long id) {
        return userAccountJpaRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorCode.CONTENT_NOT_FOUND, "user not found: id - %d".formatted(id)));
    }

    @Override
    @Transactional
    public UserAccount findByToken(String token) {
        return userAccountJpaRepository.findByToken(token).orElseThrow(
                () -> new ApplicationException(ErrorCode.CONTENT_NOT_FOUND, "user not found"));
    }

    @Override
    public UserAccount getUserAccount(String token) {
        return userAccountJpaRepository.findUserAccount(token).orElseThrow(
                        () -> new ApplicationException(ErrorCode.CONTENT_NOT_FOUND, "user not found"));
    }

    @Override
    @Transactional
    public UserAccount save(UserAccount userAccount) {
        return userAccountJpaRepository.save(userAccount);
    }
}
