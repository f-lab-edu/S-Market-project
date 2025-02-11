package com.flab.s_market.domains.security.dto.response;

public record JwtTokenResponseDTO(
    String grantType,
    String accessToken,
    String refreshToken,
    Long accessTokenExpirationTime,
    Long refreshTokenExpirationTime
){ }
