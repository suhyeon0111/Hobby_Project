package com.hoppy.app.login.auth.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomStringServiceTest {

    @Test
    @DisplayName("UUID 테스트")
    void createUUID() throws Exception {
        RandomStringService rs = new RandomStringService(6);
        String uuid = rs.nextString();
        System.out.println("uuid = " + uuid);
        assertThat(uuid.length()).isEqualTo(9);
    }
}
