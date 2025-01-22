package com.flab.s_market.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCodeDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String code;
}
