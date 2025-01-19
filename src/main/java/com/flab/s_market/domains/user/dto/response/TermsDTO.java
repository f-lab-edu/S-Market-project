package com.flab.s_market.domains.user.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class TermsDTO {
    private String title;
    private boolean isRequired;
    private List<TermDTO> terms;

}
