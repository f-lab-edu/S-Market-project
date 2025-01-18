package com.flab.s_market.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Term {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  // MYSQL에만 있는 기능, 속도빠름
    private Long id;

    @Column(nullable = false)
    private Integer version; // 정확히 일치값, 부동소수점 연산(소수점 연산, 정수연산)

    @Column(nullable = false, length = 255)
    private String url;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false)
    private Boolean detail; // main, sub (oneToMany)

    @Column(nullable = false)
    private Boolean isRequired;

    @Column(nullable = false)
    private Boolean additional;
}
