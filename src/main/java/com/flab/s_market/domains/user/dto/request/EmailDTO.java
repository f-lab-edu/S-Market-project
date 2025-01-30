package com.flab.s_market.domains.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDTO (
    @NotBlank(message = "이메일을 입력해야합니다.")
    @Email(message = "이메일 형식이어야 합니다.")
    String email
    ) { }