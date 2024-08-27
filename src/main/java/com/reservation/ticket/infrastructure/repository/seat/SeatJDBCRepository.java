package com.reservation.ticket.infrastructure.repository.seat;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class SeatJDBCRepository {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/concert?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static final String BASE_INSERT_SQL = "INSERT INTO seat (concert_schedule_id, occupied, occupied_at) VALUES ";

    public void bulkInsertConcertSchedules(int totalConcertSchedules, int batchSize) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            int recordCount = 0;

            StringBuilder sqlBuilder = new StringBuilder(BASE_INSERT_SQL);

            // 시작 날짜 설정
            LocalDate startDate = LocalDate.of(2024, 8, 22);

            for (int scheduleId = 1; scheduleId <= totalConcertSchedules; scheduleId++) {
                for (int seatIndex = 0; seatIndex < 50; seatIndex++) {  // 각 concert_schedule_id마다 50개의 seat 생성
                    if (recordCount > 0) {
                        sqlBuilder.append(",");
                    }

                    // 날짜 계산
                    LocalDateTime occupiedAt = LocalDateTime.of(startDate, LocalTime.MIDNIGHT);

                    sqlBuilder.append("(")
                            .append(scheduleId)
                            .append(", false, '")
                            .append(occupiedAt.format(formatter))
                            .append("')");

                    recordCount++;

                    // 배치가 가득 찼을 때 실행
                    if (recordCount == batchSize) {
                        try (PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {
                            pstmt.executeUpdate();
                            conn.commit();
                        } catch (SQLException e) {
                            conn.rollback();
                            e.printStackTrace();
                        }

                        sqlBuilder.setLength(0);  // SQL 빌더 초기화
                        sqlBuilder.append(BASE_INSERT_SQL);
                        recordCount = 0;
                    }
                }

                // 날짜 증가
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
