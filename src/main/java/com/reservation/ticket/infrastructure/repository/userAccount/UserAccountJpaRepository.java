package com.reservation.ticket.infrastructure.repository.userAccount;

import com.reservation.ticket.domain.entity.UserAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<UserAccount, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select u from UserAccount u where u.token = :token")
    Optional<UserAccount> findByToken(@Param("token") String token);

    @Query("select u from UserAccount u where u.token = :token")
    Optional<UserAccount> findUserAccount(@Param("token") String token);
}

