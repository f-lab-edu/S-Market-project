package com.flab.s_market.domains.member.controller.v1;

import com.flab.s_market.common.entity.ApiResponse;
import com.flab.s_market.domains.member.dto.request.EmailCodeDTO;
import com.flab.s_market.domains.member.dto.request.EmailDTO;
import com.flab.s_market.domains.member.dto.request.JoinInfoDTO;
import com.flab.s_market.domains.member.dto.request.LoginRequestDTO;
import com.flab.s_market.domains.member.service.EmailService;
import com.flab.s_market.domains.member.service.MemberService;
import com.flab.s_market.domains.security.dto.response.JwtTokenResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
@Validated
public class MemberController {
    private final MemberService memberService;
    private final EmailService emailService;

    @GetMapping("/email/checkDuplicated")
    public void getCheckEmailDuplicated(
    @RequestParam(value = "email")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @Valid String email
    ){
        memberService.checkEmailDuplicated(email);
    }

    @PostMapping("/sendCode")
    public void sendCode(@RequestBody @Valid EmailDTO emailDTO, BindingResult bindingResult) {
        emailService.sendEmail(emailDTO.email());
    }
    @PostMapping("/verifyCode")
    public ApiResponse<String> verifyCode(@RequestBody EmailCodeDTO dto){
        String responseData = emailService.verifyEmailCode(dto);
        return ApiResponse.createSuccess(responseData);
    }

    @PostMapping("/join")
    public void join(@RequestBody @Valid JoinInfoDTO dto, BindingResult bindingResult){
        memberService.join(dto);
    }

    @PostMapping("/login")
    public ApiResponse<JwtTokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto){
        JwtTokenResponseDTO responseData = memberService.login(dto);
        return ApiResponse.createSuccess(responseData);
    }

}
