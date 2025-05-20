package com.Thethirdtool.backend.security;

import com.Thethirdtool.backend.Member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User extends DefaultOAuth2User {

    private final Member member;

    public CustomOAuth2User(Member member, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        super(authorities, attributes, "id");
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}