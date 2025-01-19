package com.flab.s_market.domains.term.service;

import com.flab.s_market.domains.user.dto.response.AllTermDTO;
import com.flab.s_market.domains.term.domain.Term;
import com.flab.s_market.domains.user.dto.response.DetailTermDTO;
import com.flab.s_market.domains.user.dto.response.TermDTO;
import com.flab.s_market.domains.user.dto.response.TermsDTO;
import com.flab.s_market.domains.term.repository.TermRepository;
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
        List<Term> terms = null;
        List<TermDTO> mainTerm = null;
        List<TermDTO> additionalTerm = null;

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

    public DetailTermDTO getDetailTerms() {
        DetailTermDTO dto = null;
        List<Term> terms = null;
        Term detailTerm = terms.get(0);
        return dto;
    }
}
