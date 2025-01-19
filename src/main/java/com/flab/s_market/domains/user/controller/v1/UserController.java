package com.flab.s_market.domains.user.controller.v1;

import com.flab.s_market.common.entity.ApiResponse;
import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import com.flab.s_market.domains.user.dto.request.EmailDTO;
import com.flab.s_market.domains.user.dto.request.EmailCodeDTO;
import com.flab.s_market.domains.user.dto.response.AllTermDTO;
import com.flab.s_market.domains.user.dto.response.DetailTermDTO;
import com.flab.s_market.domains.user.service.EmailService;
import com.flab.s_market.domains.term.service.TermService;
import com.flab.s_market.domains.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final TermService termService;
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/terms/all")
    public ApiResponse<AllTermDTO> getAllTerms(){
        return ApiResponse.createSuccess(termService.getAllTerms());
    }

    @GetMapping("/terms/detail")
    public ApiResponse<DetailTermDTO> getDetailTerms(){
        return ApiResponse.createSuccess(termService.getDetailTerms());
    }

    @GetMapping("/email/checkDuplicated")
    public ApiResponse<?> getCheckEmailDuplicated(@RequestParam(value = "email") String email){
        userService.checkEmailDuplicated(email);
        return ApiResponse.createSuccessWithNoContent();
    }

    @PostMapping("/sendCode")
    public ResponseEntity sendCode(@RequestBody @Valid EmailDTO emailDTO, BindingResult bindingResult) throws MessagingException {
        emailService.sendEmail(emailDTO.getEmail());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.createSuccessWithNoContent());
    }

    @PostMapping("/verifyCode")
    public ApiResponse<?> verifyCode(@RequestBody EmailCodeDTO dto) throws NoSuchAlgorithmException {
        if(emailService.verifyEmailCode(dto.getEmail(), dto.getCode())){
            //return ApiResponse.createSuccess(emailService.makeMemberId(dto.getEmail()));
            return ApiResponse.createSuccessWithNoContent();
        }
        throw new CustomException(ErrorCode.NOT_VALID_EMAIL_CODE);
    }
}
