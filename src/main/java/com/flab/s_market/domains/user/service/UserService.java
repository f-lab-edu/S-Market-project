package com.flab.s_market.domains.user.service;

import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import com.flab.s_market.domains.term.domain.SubTerm;
import com.flab.s_market.domains.term.repository.TermRepository;
import com.flab.s_market.domains.user.domain.User;
import com.flab.s_market.domains.user.domain.UserSubTermId;
import com.flab.s_market.domains.user.dto.request.AgreedTermDTO;
import com.flab.s_market.domains.user.dto.request.JoinInfoDTO;
import com.flab.s_market.domains.user.repository.UserRepository;
import com.flab.s_market.domains.user.repository.UserSubTermRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final UserRepository userRepository;
    private final TermRepository termRepository;
    private final UserSubTermRepository userSubTermRepository;

    public void checkEmailDuplicated(String email) {
        if(userRepository.existsByEmail(email)){
            throw new CustomException(ErrorCode.EXIST_EMAIL);
        }
    }

    public void join(JoinInfoDTO dto) {
        String password = dto.password();
        String confirmPassword = dto.confirmPassword();
        List<AgreedTermDTO> terms = dto.agreedTerms();
        List<SubTerm> agreedTerms = new ArrayList<>();

        if(!password.equals(confirmPassword)){
            throw new CustomException(ErrorCode.NOT_VALID_PASSWORD);
        }
        // + 필수약관 전부 동의했나? term-subTerm 모두 가져오기
        for (AgreedTermDTO term : terms) {
            SubTerm findTerm = termRepository.findByTitleAndVersionWithJoin(term.title(), term.version())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_TERM));

            agreedTerms.add(findTerm);
        }

        // + 이메일 키 확인 코드 추가하기

        // 해시
        User savedUser = userRepository.save(dto.toUserEntity());
        for (SubTerm agreedTerm : agreedTerms) {
            UserSubTermId userSubTermId = UserSubTermId.builder()
                .userId(savedUser.getId())
                .subTerm(agreedTerm)
                .build();
            userSubTermRepository.save(dto.toUserSubTermEntity(userSubTermId, LocalDateTime.now(), savedUser));
        }
    }

}
