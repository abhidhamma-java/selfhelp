package hanji.selfhelp.etc.security.services;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

//    @Autowired
//    SecurityMapper securityMapper;
//
//    @Autowired
//    ServiceDocument serviceDocument;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> userMap = new HashMap<>();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        switch (registrationId){
            case "kakao" :
                Map<String, Object> profile = oAuth2User.getAttribute("kakao_account");
                Map<String, Object> temp = (Map<String, Object>) profile.get("profile");

                userMap.put("memberId", oAuth2User.getAttribute("id").toString());
                userMap.put("email", profile.get("email"));
                userMap.put("nickName", temp.get("nickname"));
                userMap.put("accessToken",userRequest.getAccessToken().getTokenValue());
        }

        userMap.put("loginType",registrationId);
//        serviceDocument.oauth2UserInfo(userMap);

        return oAuth2User;

    }
}
