package com.flab.s_market.domains.term.dto.response;

public record AllTermResponseDTO (
    TermsResponseDTO main, // 주 약관
    TermsResponseDTO additional// 추가 약관(신세계 멤버십)
    ) {

    public static AllTermResponseDTO from(TermsResponseDTO main, TermsResponseDTO additional){
        return new AllTermResponseDTO(main, additional);
    }
}
