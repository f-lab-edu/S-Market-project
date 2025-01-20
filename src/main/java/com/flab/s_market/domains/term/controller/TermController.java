package com.flab.s_market.domains.term.controller;

import com.flab.s_market.common.entity.ApiResponse;
import com.flab.s_market.domains.term.dto.response.AllTermResponseDTO;
import com.flab.s_market.domains.term.dto.response.DetailTermDTO;
import com.flab.s_market.domains.term.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
@Validated
public class TermController {
    private final TermService termService;

    @GetMapping("/terms/all")
    public ApiResponse<AllTermResponseDTO> getAllTerms(){
        return ApiResponse.createSuccess(termService.getAllTerms());
    }

    @GetMapping("/terms/detail")
    public ApiResponse<DetailTermDTO> getDetailTerms(){
        return ApiResponse.createSuccess(termService.getDetailTerms());
    }
}
