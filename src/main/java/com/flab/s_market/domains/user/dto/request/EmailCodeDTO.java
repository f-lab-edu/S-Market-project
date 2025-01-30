package com.flab.s_market.domains.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmailCodeDTO (
    @NotBlank
    String email,
    @NotBlank
    String code
    ){ }