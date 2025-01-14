package com.flab.s_market.user.service;

import com.flab.s_market.user.domain.Term;
import com.flab.s_market.user.dto.AllTermDTO;
import com.flab.s_market.user.dto.TermDTO;
import com.flab.s_market.user.dto.TermsDTO;
import com.flab.s_market.user.repository.TermRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TermService {

    private final TermRepository termRepository;

    public AllTermDTO getAllTerms(){
        List<Term> terms = termRepository.findByDetailAndVersion(false, 1.1d);
        List<TermDTO> mainTerm = terms.stream().filter(element -> !element.isAdditional())
            .map(element->TermDTO.builder()
                .title(element.getTitle())
                .isRequired(element.isRequired())
                .build()).toList();

        List<TermDTO> additionalTerm = terms.stream().filter(element -> !element.isAdditional())
            .map(element->TermDTO.builder()
                .title(element.getTitle())
                .isRequired(element.isRequired())
                .build()).toList();

        TermsDTO mainTermDTO = TermsDTO.builder()
            .title("이용약관에 먼저 동의해주세요.")
            .isRequired(true)
            .terms(mainTerm)
            .build();

        TermsDTO additionalTermDTO = TermsDTO.builder()
            .title("신규 가입 회원에게만 드려요.\n 유니버스 크럽 3개월 무료 이용!")
            .isRequired(false)
            .terms(additionalTerm)
            .build();

        return AllTermDTO.builder()
            .main(mainTermDTO)
            .additional(additionalTermDTO)
            .build();
    }
}
