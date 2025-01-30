package com.flab.s_market.domains.user.controller.v1;

import com.flab.s_market.common.entity.ApiResponse;
import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import com.flab.s_market.domains.user.dto.request.EmailCodeDTO;
import com.flab.s_market.domains.user.dto.request.EmailDTO;
import com.flab.s_market.domains.user.dto.request.JoinInfoDTO;
import com.flab.s_market.domains.user.service.EmailService;
import com.flab.s_market.domains.user.service.UserService;
import jakarta.validation.Valid;
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
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/email/checkDuplicated")
    public void getCheckEmailDuplicated(@RequestParam(value = "email") String email){
        userService.checkEmailDuplicated(email);
    }

    @PostMapping("/sendCode")
    public void sendCode(@RequestBody @Valid EmailDTO emailDTO, BindingResult bindingResult) {
        emailService.sendEmail(emailDTO.email());
    }
    @PostMapping("/verifyCode")
    // 암호키 주기 (대칭키)AES-2048, 1024
    public ApiResponse<String> verifyCode(@RequestBody EmailCodeDTO dto) {
        if(emailService.verifyEmailCode(dto.email(), dto.code())){ //
            return ApiResponse.createSuccess(dto.email()); // 암호화 값을 던지기
        }
        throw new CustomException(ErrorCode.NOT_VALID_EMAIL_CODE);
    }

    @PostMapping("/join")
    // 암호키
    public void join(@RequestBody JoinInfoDTO dto, BindingResult bindingResult){
        userService.join(dto);
    }

}
