package com.flab.s_market.user.service;

import com.flab.s_market.user.domain.Term;
import com.flab.s_market.user.dto.response.AllTermDTO;
import com.flab.s_market.user.dto.response.DetailTermDTO;
import com.flab.s_market.user.dto.response.TermDTO;
import com.flab.s_market.user.dto.response.TermsDTO;
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

    public DetailTermDTO getDetailTerms() {
        List<Term> terms = termRepository.findByDetailAndVersion(true, 1.1);
        Term detailTerm = terms.get(0);
        return DetailTermDTO.builder()
            .url(detailTerm.getUrl())
            .version(detailTerm.getVersion())
            .title(detailTerm.getTitle())
            .build();
    }
}
