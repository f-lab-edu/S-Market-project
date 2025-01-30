package com.flab.s_market.domains.user.service;

import com.flab.s_market.common.config.AES128Config;
import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import com.flab.s_market.domains.user.dto.request.EmailCodeDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
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
    private final AES128Config aes128Config;
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
    private MimeMessage createEmailForm(String email) throws MessagingException {

        String authCode = createdCode();

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("S market 인증 코드");
        message.setFrom(configEmail);
        message.setText(setContext(authCode), "utf-8", "html");
        setDataExpire(email, authCode, 60 * 30L);

        return message;
    }


    // 메일 보내기
    public void sendEmail(String toEmail){
        if (existData(toEmail)) {
            deleteData(toEmail);
        }
        try {
            MimeMessage emailForm = createEmailForm(toEmail);
            mailSender.send(emailForm);
        }catch(Exception e){
            throw new CustomException(ErrorCode.MAIL_SYSTEM_ERROR, Map.of("email", toEmail), log::warn);
        }
    }

    // 코드 검증
    public String verifyEmailCode(EmailCodeDTO dto){
        String email = dto.email();
        String code = dto.code();
        String codeFoundByEmail = getData(dto.email());
        System.out.println(codeFoundByEmail);
        if (codeFoundByEmail == null) {
            throw new CustomException(ErrorCode.NOT_VALID_EMAIL_CODE,
                Map.of("email", email, "emailCode", code), log::info);
        }
        deleteData(email);
        String emailKey = aes128Config.encryptAes(email);
        setDataExpire(email, emailKey, 60*30L);

        log.info("enc = {}", emailKey);

        return emailKey;
    }

    public String makeMemberId(String email) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(email.getBytes());
        md.update(LocalDateTime.now().toString().getBytes());
        byte[] digest = md.digest();
        return Base64.getEncoder().encodeToString(digest);
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
