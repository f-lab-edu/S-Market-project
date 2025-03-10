package com.flab.s_market.domains.security.dto.request;

import jakarta.validation.constraints.NotBlank;

public record JwtTokenLogoutRequestDTO(
    @NotBlank(message = "accessToken을 입력해주세요")
    String accessToken
) {

}
