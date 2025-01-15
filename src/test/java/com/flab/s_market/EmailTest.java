package com.flab.s_market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.flab.s_market.common.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmailTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void redisTest(){
        String email = "ghenrhkwk88@gmail.com";
        String code = "aaa111";

        redisUtil.setDataExpire(email, code, 60*60L);

        assertTrue(redisUtil.existData("ghenrhkwk88@gmail.com"));
        assertFalse(redisUtil.existData("ghenrhkwk88@test.com"));
        assertEquals(redisUtil.getData(email), code);
    }
}
