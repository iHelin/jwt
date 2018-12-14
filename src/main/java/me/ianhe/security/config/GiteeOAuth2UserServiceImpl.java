package me.ianhe.security.config;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author iHelin
 * @date 2018-12-14 11:08
 */
@Service
public class GiteeOAuth2UserServiceImpl implements OAuth2UserService {

    private RestTemplate restTemplate;

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE =
            new ParameterizedTypeReference<Map<String, Object>>() {
            };

    public GiteeOAuth2UserServiceImpl() {
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
    }

    /**
     * Gitee获取用户信息的方式不是在请求头里面加Bearer accessToken，而是通过请求参数获取?access_token=xxx的形式
     *
     * @param userRequest
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String uri = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        String accessToken = userRequest.getAccessToken().getTokenValue();
        uri = uri + "?access_token=" + accessToken;
        Map<String, Object> userAttributes = restTemplate.getForObject(uri, Map.class);
        Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
    }
}
