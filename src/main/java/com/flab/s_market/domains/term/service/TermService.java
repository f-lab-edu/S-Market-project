package com.flab.s_market.domains.term.service;

import com.flab.s_market.domains.term.domain.Term;
import com.flab.s_market.domains.term.dto.response.AllTermResponseDTO;
import com.flab.s_market.domains.term.dto.response.DetailTermDTO;
import com.flab.s_market.domains.term.dto.response.TermResponseDTO;
import com.flab.s_market.domains.term.dto.response.TermsResponseDTO;
import com.flab.s_market.domains.term.repository.SubTermRepository;
import com.flab.s_market.domains.term.repository.TermRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TermService {

    private final TermRepository termRepository;
    private static final String mainTitle = "이용약관에 먼저 동의해주세요";
    private static final String additionalTitle = "신규 가입 회원에게만 드려요.\\n 유니버스 크럽 3개월 무료 이용!";
    public AllTermResponseDTO getAllTerms(){
        List<Term> terms = termRepository.findAll(); // 쿼리 확인
        List<TermResponseDTO> mainTermList = new ArrayList<>();
        List<TermResponseDTO> additionalTermList = new ArrayList<>();

        for(Term term : terms){
            if(!term.getAdditional()){
                mainTermList.add(TermResponseDTO.from(term));
            }else{
                additionalTermList.add(TermResponseDTO.from(term));
            }
        }

        TermsResponseDTO mainDto = TermsResponseDTO.from(mainTitle, mainTermList);
        TermsResponseDTO additionalDto = TermsResponseDTO.from(additionalTitle, additionalTermList);

        return AllTermResponseDTO.from(mainDto, additionalDto);
    }

    public DetailTermDTO getDetailTerms(Long termId, Integer version) {
          return null;
//        return subTermRepository.findByTermIdAndVersion(termId, version);
//            subTermRepository.findByTermIdAndVersion(termId, version);
//            .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_TERM, Map.of("termId", termId, "version", version),
//                log::info));
        // optional리턴해서 없으면 예외처리
    }
}
