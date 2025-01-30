package com.flab.s_market.common.config;

import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AES128Config {
    private static final Charset ENCODING_TYPE = StandardCharsets.UTF_8;
    private static final String INSTANCE_TYPE = "AES/CBC/PKCS5Padding";
    private static final int AES_KEY_SIZE = 16;

    @Value("${aes.secret-key}")
    private String secretKey;
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;

    @PostConstruct
    public void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];   // 16bytes = 128bits
        secureRandom.nextBytes(iv); // 16바이트 크기의 난수 바이트 배열 생성
        ivParameterSpec = new IvParameterSpec(iv);

        byte[] keyBytes = secretKey.getBytes(ENCODING_TYPE);
        keyBytes = Arrays.copyOf(keyBytes, AES_KEY_SIZE);
        secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        cipher = Cipher.getInstance(INSTANCE_TYPE);

    }

    // AES 암호화
    public String encryptAes(String plaintext){
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryted = cipher.doFinal(plaintext.getBytes(ENCODING_TYPE)); // 데이터 암호화
            return new String(Base64.getEncoder().encode(encryted), ENCODING_TYPE); // 문자열 인코딩 반환
        } catch (Exception e) {
            throw new CustomException(ErrorCode.ENCRYPTION_FAILED);
        }
    }

    // AES 복호화
    public String decryptAes(String plaintext) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decoded = Base64.getDecoder().decode(plaintext.getBytes(ENCODING_TYPE));
            return new String(cipher.doFinal(decoded), ENCODING_TYPE); // 데이터 복호화, 인코딩 반환
        } catch (Exception e) {
            throw new CustomException(ErrorCode.DECRYPTION_FAILED);
        }
    }
}
