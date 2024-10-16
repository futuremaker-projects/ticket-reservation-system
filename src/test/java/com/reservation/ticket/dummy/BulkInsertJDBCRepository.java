package com.reservation.ticket.dummy;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class BulkInsertJDBCRepository {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/concert?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static final String BASE_INSERT_SQL = "INSERT INTO user_account (username, token, point, version) VALUES ";

    public void bulkInsertConcertSchedules(int totalRecords, int batchSize) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);

            int recordCount = 0;

            StringBuilder sqlBuilder = new StringBuilder(BASE_INSERT_SQL);

            // 시작 날짜 설정
            for (int i = 0; i < totalRecords; i++) {
                if (recordCount > 0) {
                    sqlBuilder.append(",");
                }
                // concert_id 계산 (1에서 5000까지 순차적으로)

                sqlBuilder.append("('")
                        .append("user%d".formatted(i))
                        .append("', '")
                        .append(generateToken())
                        .append("', '")
                        .append('0')
                        .append("', '")
                        .append('0')
                        .append("')");

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

    private String generateToken() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(uuid.lastIndexOf("-") + 1);
    }

}
