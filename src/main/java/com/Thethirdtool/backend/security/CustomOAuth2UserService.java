package com.Thethirdtool.backend.security;

import com.Thethirdtool.backend.Member.domain.Member;
import com.Thethirdtool.backend.Member.presentation.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String kakaoId = attributes.get("id").toString();
        String nickname = (String) profile.get("nickname");
        String email = (String) kakaoAccount.get("email"); // null일 수 있음

        // 회원 저장 또는 조회
        Member member = memberRepository.findByKakaoId(kakaoId)
                                        .orElseGet(() -> memberRepository.save(
                                                Member.of(kakaoId, nickname, email)
                                                                              ));

        // 권한 부여
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // 반환: DefaultOAuth2User 사용
        return new DefaultOAuth2User(
                authorities,
                attributes,
                "id" // 고유 식별자로 사용할 키 (attributes에서 가져올 이름)
        );
    }
}