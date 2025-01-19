package com.flab.s_market.domains.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class TermDTO {
    private String title;
    private boolean isRequired;
}
