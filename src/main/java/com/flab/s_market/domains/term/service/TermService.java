package com.flab.s_market.domains.term.service;

import com.flab.s_market.domains.term.domain.Term;
import com.flab.s_market.domains.term.dto.response.AllTermResponseDTO;
import com.flab.s_market.domains.term.dto.response.DetailTermDTO;
import com.flab.s_market.domains.term.dto.response.TermResponseDTO;
import com.flab.s_market.domains.term.dto.response.TermsResponseDTO;
import com.flab.s_market.domains.term.repository.SubTermRepository;
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
    private final SubTermRepository subTermRepository;
    private static final Integer TERM_VERSION = 1;
    private static final String mainTitle = "이용약관에 먼저 동의해주세요";
    private static final String additionalTitle = "신규 가입 회원에게만 드려요.\\n 유니버스 크럽 3개월 무료 이용!";
    public AllTermResponseDTO getAllTerms(){
        List<Term> terms = termRepository.findTermByVersionWithJoin(TERM_VERSION);

        List<TermResponseDTO> mainTermList =
            terms.stream()
                .filter(term -> !term.getAdditional())
                .map(TermResponseDTO::of)
                .toList();

        List<TermResponseDTO> additionalTermList =
            terms.stream()
                .filter(Term::getAdditional)
                .map(TermResponseDTO::of)
                .toList();

        TermsResponseDTO mainDto = TermsResponseDTO.convertTermResponseDTOToTermsResponseDTO(mainTitle, mainTermList);
        TermsResponseDTO additionalDto = TermsResponseDTO.convertTermResponseDTOToTermsResponseDTO(additionalTitle, additionalTermList);

        return AllTermResponseDTO.convertTermsResponseDTOToAllTermResponseDTO(mainDto, additionalDto);
    }

    public DetailTermDTO getDetailTerms() {
        return subTermRepository.findByVersionAndUrl(TERM_VERSION);
    }
}
