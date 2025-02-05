package com.flab.s_market.domains.member.service;

import com.flab.s_market.common.config.EncryptionService;
import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import com.flab.s_market.domains.member.domain.Member;
import com.flab.s_market.domains.member.domain.MemberSubTermId;
import com.flab.s_market.domains.member.dto.request.LoginRequestDTO;
import com.flab.s_market.domains.security.service.JwtTokenProvider;
import com.flab.s_market.domains.security.dto.response.JwtTokenResponseDTO;
import com.flab.s_market.domains.term.domain.SubTerm;
import com.flab.s_market.domains.term.domain.Term;
import com.flab.s_market.domains.term.repository.TermRepository;
import com.flab.s_market.domains.member.dto.request.AgreedTermDTO;
import com.flab.s_market.domains.member.dto.request.JoinInfoDTO;
import com.flab.s_market.domains.member.repository.MemberRepository;
import com.flab.s_market.domains.member.repository.MemberSubTermRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final MemberRepository userRepository;
    private final TermRepository termRepository;
    private final MemberSubTermRepository memberSubTermRepository;
    private final EmailService emailService;
    private final EncryptionService encryptionService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public void checkEmailDuplicated(String email) {
        if(userRepository.existsByEmail(email)){
            throw new CustomException(ErrorCode.EXIST_EMAIL, Map.of("email", email), log::info);
        }
    }

    public void join(JoinInfoDTO dto) {
        String rawPassword = dto.password();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        String confirmPassword = dto.confirmPassword();
        String emailKey = dto.emailKey();
        String email = encryptionService.decrypt(emailKey);

        if(userRepository.existsByEmail(email)){
            throw new CustomException(ErrorCode.EXIST_USER, Map.of("email", email), log::info);
        }

        if(!emailService.existEmailData(email)){
            throw new CustomException(ErrorCode.DECRYPTION_FAILED, Map.of("emailKey", emailKey), log::info);
        }

        if(!ObjectUtils.equals(rawPassword, confirmPassword)){ // NULL?
            throw new CustomException(ErrorCode.NOT_VALID_PASSWORD,
                Map.of("password", rawPassword, "confirmPassword", confirmPassword), log::info);
        }

        List<AgreedTermDTO> agreedTermsDTO = dto.agreedTerms();
        Map<String, Integer> agreedTermsMap = agreedTermsDTO.stream().collect(
            Collectors.toMap(AgreedTermDTO::title,AgreedTermDTO::version));
        List<SubTerm> allTermsInDB = termRepository.findByTermIdAndVersionWithJoin();

        Member savedMember = userRepository.save(dto.toUserEntity(email, encPassword));
        for (SubTerm subTerm : allTermsInDB) {
            Term term = subTerm.getTerm();
            if(term.getIsRequired()){
                if(!agreedTermsMap.containsKey(term.getTitle())){
                    throw new CustomException(ErrorCode.NOT_ALL_AGREED_REQUIRED_TERMS,
                        Map.of("agreedTermsDTO", agreedTermsDTO, "allTermsInDB", allTermsInDB), log::info);
                }else{
                    if(agreedTermsMap.get(term.getTitle()).equals(subTerm.getId().getVersion())){
                        saveUserSubTerm(savedMember, dto, true, subTerm);
                    }else{
                        throw new CustomException(ErrorCode.NOT_MATCH_TERM_VERSION,
                            Map.of("agreedSubTerm", subTerm, "version", agreedTermsMap.get(term.getTitle())), log::info);
                    }
                }
            }else{
                if(!agreedTermsMap.containsKey(term.getTitle())){
                    saveUserSubTerm(savedMember, dto, false, subTerm);
                }else{
                    if(agreedTermsMap.get(term.getTitle()).equals(subTerm.getId().getVersion())){
                        saveUserSubTerm(savedMember, dto, false, subTerm);
                    }else{
                        throw new CustomException(ErrorCode.NOT_MATCH_TERM_VERSION,
                            Map.of("agreedSubTerm", subTerm, "version", agreedTermsMap.get(term.getTitle())), log::info);
                    }
                }
            }
        }

    }

    private void saveUserSubTerm(Member member, JoinInfoDTO dto, boolean agree, SubTerm subTerm) {
        MemberSubTermId memberSubTermId = MemberSubTermId.builder()
            .memberId(member.getId())
            .subTerm(subTerm)
            .build();
        memberSubTermRepository.save(dto.toUserSubTermEntity(memberSubTermId, agree, member));
    }

    public JwtTokenResponseDTO login(LoginRequestDTO dto){
        String email = dto.email();
        String password = dto.password();

        // 1. email + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 사용자 검증(사용자 비밀번호 체크)가 이뤄지는 부분
        // authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        // authenticated값이 true
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenResponseDTO jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

}
