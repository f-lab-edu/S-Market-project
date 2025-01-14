package com.flab.s_market.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class DetailTermDTO {
    private String url;
    private double version;
    private String title;
}
