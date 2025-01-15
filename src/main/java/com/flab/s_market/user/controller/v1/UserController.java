package com.flab.s_market.user.controller.v1;

import com.flab.s_market.common.ApiResponse;
import com.flab.s_market.user.dto.AllTermDTO;
import com.flab.s_market.user.dto.DetailTermDTO;
import com.flab.s_market.user.dto.EmailDTO;
import com.flab.s_market.user.service.EmailService;
import com.flab.s_market.user.service.TermService;
import com.flab.s_market.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
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

    @GetMapping("/checkEmail")
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
}
