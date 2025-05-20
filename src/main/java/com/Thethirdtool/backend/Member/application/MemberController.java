package com.Thethirdtool.backend.Member.application;

import com.Thethirdtool.backend.Card.dto.response.ApiResponse;
import com.Thethirdtool.backend.security.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Long>> getMyId(@AuthenticationPrincipal CustomOAuth2User userDetails) {
        return ResponseEntity.ok(ApiResponse.ok(userDetails.getMember().getId()));
    }
}