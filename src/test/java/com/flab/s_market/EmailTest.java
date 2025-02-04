package com.flab.s_market;

import com.flab.s_market.common.config.EncryptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class EmailTest {

    @Autowired
    private EncryptionService encryptionService;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    @DisplayName("Aes128 암호화가 잘 되는지 확인 테스트")
    public void aes128Test(){
        String text = "this is test";
        String enc = encryptionService.encrypt(text);
        String dec = encryptionService.decrypt(enc);
        log.info("enc = {}", enc);
        log.info("dec = {}", dec);
    }
}
