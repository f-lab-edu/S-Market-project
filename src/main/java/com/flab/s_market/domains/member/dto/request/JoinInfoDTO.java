package com.flab.s_market.domains.member.dto.request;

import com.flab.s_market.domains.member.domain.Member;
import com.flab.s_market.domains.member.domain.MemberSubTerm;
import com.flab.s_market.domains.member.domain.MemberSubTermId;
import com.flab.s_market.domains.security.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Collections;
import java.util.List;

public record JoinInfoDTO (
    @NotBlank(message = "이메일 키를 입력해주세요.")
    String emailKey,
    @NotBlank(message = "이름을 입력해주세요.")
    String userName,
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    String password,
    @NotBlank(message = "재확인 비밀번호를 입력해주세요.")
    String confirmPassword,
    List<AgreedTermDTO> agreedTerms
    ){
    public Member toUserEntity(String email, String encPassword) {
        return Member.builder()
            .name(userName)
            .email(email)
            .password(encPassword)
            .roles(Collections.singletonList(Role.USER.name()))
            .build();
    }
    public MemberSubTerm toUserSubTermEntity(MemberSubTermId id, boolean agree, Member member){
        return new MemberSubTerm(id, agree, member);
    }
}