package com.flab.s_market.domains.term.controller;

import com.flab.s_market.common.entity.ApiResponse;
import com.flab.s_market.domains.term.dto.response.AllTermResponseDTO;
import com.flab.s_market.domains.term.dto.response.DetailTermDTO;
import com.flab.s_market.domains.term.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
@Validated
public class TermController {
    private final TermService termService;

    @GetMapping("/terms/all")
    public ApiResponse<AllTermResponseDTO> getAllTerms(){
        AllTermResponseDTO responseDTO = termService.getAllTerms();
        return ApiResponse.createSuccess(responseDTO);
    }

    @GetMapping("/terms/detail")
    public ApiResponse<DetailTermDTO> getDetailTerms(
        @RequestParam(name = "termId") Long termId,
        @RequestParam(name = "version") Integer version
    ){
        DetailTermDTO responseDTO = termService.getDetailTerms(termId, version);
        return ApiResponse.createSuccess(responseDTO);
    }
}
