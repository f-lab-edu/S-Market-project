package com.flab.s_market.domains.user.service;

import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import com.flab.s_market.domains.user.repository.UserRepository;
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

    public void checkEmailDuplicated(String email) {
        if(userRepository.existsByEmail(email)){
            throw new CustomException(ErrorCode.EXIST_EMAIL);
        }
    }
}
