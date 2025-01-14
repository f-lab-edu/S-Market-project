package com.flab.s_market.user.service;

import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import com.flab.s_market.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void checkEmailDuplicated(String email) {
        if(userRepository.existsByEmail(email)){
            throw new CustomException(ErrorCode.EXIST_EMAIL);
        }
    }
}
