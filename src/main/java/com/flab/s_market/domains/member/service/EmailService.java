package com.flab.s_market.domains.member.service;

import com.flab.s_market.common.config.EncryptionService;
import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import com.flab.s_market.domains.member.dto.request.EmailCodeDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;
    private final EncryptionService encryptionService;
    private Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String configEmail;

    private String createdCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <=57 || i >=65) && (i <= 90 || i>= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    private String setContext(String code) {
        Context context = new Context();
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        context.setVariable("code", code);


        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);

        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine.process("mail", context);
    }


    // 메일 반환
    private MimeMessage createEmailForm(String email, String authCode) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("S market 인증 코드");
        message.setFrom(configEmail);
        message.setText(setContext(authCode), "utf-8", "html");

        return message;
    }


    // 메일 보내기
    public void sendEmail(String toEmail){
        if (existEmailData(toEmail)) {
            throw new CustomException(ErrorCode.NOT_PASSED_FIVE_MINUTES, Map.of("email", toEmail), log::info);
        }

        try {
            String authCode = createdCode();
            MimeMessage emailForm = createEmailForm(toEmail, authCode);
            setDataWithTTL(toEmail, authCode, 60 * 5L);
            mailSender.send(emailForm);
        }catch(Exception e){
            throw new CustomException(ErrorCode.MAIL_SYSTEM_ERROR, Map.of("email", toEmail), log::warn, e); // i/o exception
        }
    }

    // 코드 검증
    public String verifyEmailCode(EmailCodeDTO dto){
        String email = dto.email();
        String code = dto.code();
        Optional<String> codeFoundByEmail = getEmailDataIfExist(dto.email());
        if (codeFoundByEmail.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_VALID_EMAIL_CODE,
                Map.of("email", email, "emailCode", code), log::info);
        }
        deleteEmailData(email);
        String emailKey = encryptionService.encrypt(email);
        setDataWithTTL(email, emailKey, 60*30L);

        log.info("enc = {}", emailKey);

        return emailKey;
    }

    public void setDataWithTTL(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public Optional<String> getEmailDataIfExist(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return Optional.ofNullable(valueOperations.get(key));
    }

    public boolean existEmailData(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void deleteEmailData(String key) {
        Optional<String> emailDataIfExist = getEmailDataIfExist(key);
        if(emailDataIfExist.isPresent()){
            redisTemplate.delete(key);
        }
    }
}
