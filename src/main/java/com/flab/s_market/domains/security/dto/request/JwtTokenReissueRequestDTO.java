package com.flab.s_market.domains.security.dto.request;

import jakarta.validation.constraints.NotBlank;

public record JwtTokenReissueRequestDTO(
    @NotBlank(message = "accessToken을 입력해주세요")
    String accessToken,
    @NotBlank(message = "refreshToken을 입력해주세요")
    String refreshToken
){ }
