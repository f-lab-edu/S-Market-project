package com.flab.s_market.user.controller.v1;

import com.flab.s_market.common.ApiResponse;
import com.flab.s_market.user.dto.AllTermDTO;
import com.flab.s_market.user.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
public class UserController {
    private final TermService termService;

    @GetMapping("/terms/all")
    public ApiResponse<AllTermDTO> getAllTerms(){
        return ApiResponse.createSuccess(termService.getAllTerms());
    }
}
