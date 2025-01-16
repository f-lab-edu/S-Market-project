package com.flab.s_market.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class AllTermDTO {
    private TermsDTO main;
    private TermsDTO additional;

}
