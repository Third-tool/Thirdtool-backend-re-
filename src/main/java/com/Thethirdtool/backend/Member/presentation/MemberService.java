package com.Thethirdtool.backend.Member.presentation;

import com.Thethirdtool.backend.Deck.domain.Deck;
import com.Thethirdtool.backend.Member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 로그인한 유저 ID 기준으로 자신의 덱 목록 조회
    @Transactional
    public List<Deck> getMyDecks(Long memberId) {
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        return member.getDecks(); // 연관된 덱 리스트 반환
    }
}
