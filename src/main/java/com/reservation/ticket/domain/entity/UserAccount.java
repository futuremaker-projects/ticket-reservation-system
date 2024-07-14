package com.reservation.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20) NOT NULL")
    private String username;

    private String token;
    private int point;

    public UserAccount(Long id, String username, String token, int point) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.point = point;
    }

    public UserAccount(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserAccount of(Long id, String username) {
        return new UserAccount(id, username);
    }

    public static UserAccount of(Long id, String username, String token) {
        return new UserAccount(id, username, token, 0);
    }

    public void saveToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount userAccount)) return false;
        return id != null && id.equals(userAccount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
