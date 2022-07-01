package com.hoppy.app.login.auth.handler.sample.success.service;

import static org.assertj.core.api.Assertions.*;

import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.login.auth.service.LoadUserService;
import com.hoppy.app.login.auth.service.SocialLoadStrategy;
import com.hoppy.app.login.auth.token.AuthToken;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "mockUSer")
public class KakaoServiceTest {

    @Autowired
    private SocialLoadStrategy socialLoadStrategy;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    /**
     * access token을 직접 받아와서 넣어줘야 하는 번거로움이 있음.
     */

    private static final String accessToken = "l86bHBne1JTg_wBgauTFW8DCKIkLPwzWHnQjgm5pCj1z7AAAAYG5suEs";

    @Test
    public void 사용자_정보_요청() throws Exception {
        Map<String, Object> userInfo = socialLoadStrategy.getUserInfo(accessToken);
        
        AuthToken token = authTokenProvider.createUserAuthToken(userInfo.get("id").toString());

        System.out.println("token = " + token.getToken());

        assertThat(userInfo).isNotNull();
        assertThat(userInfo.get("id")).isEqualTo(2249461729L);
        assertThat(userInfo.get("nickname")).isEqualTo("최대한");
        /**
         * 생성한 유저의 socialId와, jwt를 파싱해서 얻은 socialId 값이 같은지 확인.
         */
        assertThat(userInfo.get("id").toString()).isEqualTo(authTokenProvider.getSocialId(token.getToken()));
    }
}
