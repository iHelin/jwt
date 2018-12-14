package me.ianhe.security.config;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author iHelin
 * @date 2018-12-14 11:08
 */
@Service
public class CustomOAuth2UserService implements OAuth2UserService {

    private RestOperations restOperations;

    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE =
            new ParameterizedTypeReference<Map<String, Object>>() {
            };

    public CustomOAuth2UserService() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String uri = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        String tokenValue = userRequest.getAccessToken().getTokenValue();
        uri = uri + "?access_token=" + tokenValue;
        RequestEntity<?> request = null;
        try {
            request = new RequestEntity<>(HttpMethod.GET, new URI(uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<Map<String, Object>> response = restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
        Map<String, Object> userAttributes = response.getBody();
        Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
    }
}
