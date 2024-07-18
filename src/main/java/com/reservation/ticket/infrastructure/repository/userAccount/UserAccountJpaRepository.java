package com.reservation.ticket.infrastructure.repository.userAccount;

import com.reservation.ticket.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByToken(String token);

}
