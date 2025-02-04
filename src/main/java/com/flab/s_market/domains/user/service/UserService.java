package com.flab.s_market.domains.user.service;

import com.flab.s_market.common.config.EncryptionService;
import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import com.flab.s_market.domains.term.domain.SubTerm;
import com.flab.s_market.domains.term.domain.Term;
import com.flab.s_market.domains.term.repository.TermRepository;
import com.flab.s_market.domains.user.domain.User;
import com.flab.s_market.domains.user.domain.UserSubTermId;
import com.flab.s_market.domains.user.dto.request.AgreedTermDTO;
import com.flab.s_market.domains.user.dto.request.JoinInfoDTO;
import com.flab.s_market.domains.user.repository.UserRepository;
import com.flab.s_market.domains.user.repository.UserSubTermRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final EmailService emailService;
    private final EncryptionService encryptionService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

        User savedUser = userRepository.save(dto.toUserEntity(email, encPassword));
        for (SubTerm subTerm : allTermsInDB) {
            Term term = subTerm.getTerm();
            if(term.getIsRequired()){
                if(!agreedTermsMap.containsKey(term.getTitle())){
                    throw new CustomException(ErrorCode.NOT_ALL_AGREED_REQUIRED_TERMS,
                        Map.of("agreedTermsDTO", agreedTermsDTO, "allTermsInDB", allTermsInDB), log::info);
                }else{
                    if(agreedTermsMap.get(term.getTitle()).equals(subTerm.getId().getVersion())){
                        saveUserSubTerm(savedUser, dto, true, subTerm);
                    }else{
                        throw new CustomException(ErrorCode.NOT_MATCH_TERM_VERSION,
                            Map.of("agreedSubTerm", subTerm, "version", agreedTermsMap.get(term.getTitle())), log::info);
                    }
                }
            }else{
                if(!agreedTermsMap.containsKey(term.getTitle())){
                    saveUserSubTerm(savedUser, dto, false, subTerm);
                }else{
                    if(agreedTermsMap.get(term.getTitle()).equals(subTerm.getId().getVersion())){
                        saveUserSubTerm(savedUser, dto, false, subTerm);
                    }else{
                        throw new CustomException(ErrorCode.NOT_MATCH_TERM_VERSION,
                            Map.of("agreedSubTerm", subTerm, "version", agreedTermsMap.get(term.getTitle())), log::info);
                    }
                }
            }
        }

    }

    private void saveUserSubTerm(User user, JoinInfoDTO dto, boolean agree, SubTerm subTerm) {
        UserSubTermId userSubTermId = UserSubTermId.builder()
            .userId(user.getId())
            .subTerm(subTerm)
            .build();
        userSubTermRepository.save(dto.toUserSubTermEntity(userSubTermId, agree, user));
    }

}
