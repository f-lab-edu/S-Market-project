package com.flab.s_market.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Term {
    @Id @GeneratedValue
    @Column(name = "term_id")
    private Long id;
    private double version;
    private String url;
    private String title;
    private boolean detail;
    private boolean isRequired;
    private boolean additional;

    public Term(Long id, double version, String url, String title, boolean detail, boolean isRequired, boolean additional) {
        this.id = id;
        this.version = version;
        this.url = url;
        this.title = title;
        this.detail = detail;
        this.isRequired = isRequired;
        this.additional = additional;
    }

    public Term() {

    }
}
