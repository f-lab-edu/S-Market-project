package com.flab.s_market.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String name;
    private String password;
    private String emailKey;

    public User(Long id, String email, String name, String password, String emailKey) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.emailKey = emailKey;
    }

    public User() {
    }
}
