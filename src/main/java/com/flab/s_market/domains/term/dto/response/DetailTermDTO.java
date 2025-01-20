package com.flab.s_market.domains.term.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class DetailTermDTO {
    private String url;
    private Integer version;
    private String title;

    @QueryProjection
    public DetailTermDTO(String url, Integer version, String title) {
        this.url = url;
        this.version = version;
        this.title = title;
    }
}
