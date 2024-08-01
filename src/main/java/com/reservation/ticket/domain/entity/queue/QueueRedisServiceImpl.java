package com.reservation.ticket.domain.entity.queue;

import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.entity.userAccount.UserAccountRepository;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueRedisServiceImpl  {

    private final UserAccountRepository userAccountRepository;
    private final QueueRepository queueRepository;

    public String createQueue(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId);
        String token = generateToken();
        // 생성된 토큰을 사용자 정보에 저장
        userAccount.saveToken(token);
        queueRepository.save(QueueStatement.of(userAccount, token, QueueStatus.WAIT));
        // response header에 넣어주기 위한 토큰을 리턴
        return token;
    }

    public List<QueueCommand.Get> selectQueueByStatus(QueueStatus status) {
        return List.of();
    }

    public void renewQueueExpirationDate(String token) {

    }

    public void expireQueue(String token) {

    }

    public QueueCommand.Get getQueueByToken(String token) {
        return null;
    }

    public void verifyQueue(String token) {
        QueueEntity queue = queueRepository.getQueueByToken(token);

    }

    public void changeTokenStatusToExpire() {

    }

    public void changeTokenStatusToActive() {

    }

    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.lastIndexOf("-") + 1);
    }

}
