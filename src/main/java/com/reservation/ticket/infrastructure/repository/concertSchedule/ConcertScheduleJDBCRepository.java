package com.reservation.ticket.infrastructure.repository.concertSchedule;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Repository
public class ConcertScheduleJDBCRepository {

    // jdbc:mysql://220.88.103.159:13306/concert?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true

    public void bulkInsertConcertSchedules(int totalRecords, int batchSize) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            int recordCount = 0;

            StringBuilder sqlBuilder = new StringBuilder(BASE_INSERT_SQL);

            // 시작 날짜 설정
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            LocalDate currentDate = startDate;

            for (int i = 0; i < totalRecords; i++) {
                if (recordCount > 0) {
                    sqlBuilder.append(",");
                }

                // 날짜 계산
                LocalDateTime openedAt = LocalDateTime.of(currentDate, LocalTime.MIDNIGHT);
                sqlBuilder.append("(50, '")
                        .append(openedAt.format(formatter))
                        .append("', 1)");

                // 날짜 증가
                currentDate = currentDate.plusDays(1);
                if (currentDate.isAfter(LocalDate.of(2024, 12, 31))) {
                    currentDate = startDate;
                }

                recordCount++;

                if (recordCount == batchSize) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {
                        pstmt.executeUpdate();
                        conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                        e.printStackTrace();
                    }

                    sqlBuilder.setLength(0);
                    sqlBuilder.append(BASE_INSERT_SQL);
                    recordCount = 0;
                }
            }

            // 남아 있는 배치 작업 실행
            if (recordCount > 0) {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {
                    pstmt.executeUpdate();
                    conn.commit();
                } catch (SQLException e) {
                    conn.rollback();
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}