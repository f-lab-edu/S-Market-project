package com.flab.s_market.domains.user.dto.request;

import com.flab.s_market.domains.user.domain.UserSubTerm;
import com.flab.s_market.domains.user.domain.User;
import com.flab.s_market.domains.user.domain.UserSubTermId;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class JoinInfoDTO {
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "이메일 키를 입력해주세요.")
    private String emailKey;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "재확인 비밀번호를 입력해주세요.")
    private String confirmPassword;

    private List<AgreedTerm> agreedTerms;

    public User toUserEntity() {
        return new User(email, userName, password, emailKey);
    }
    public UserSubTerm toUserSubTermEntity(UserSubTermId id, LocalDateTime agreeDate, User user){
        return new UserSubTerm(id, agreeDate, user);
    }
}
