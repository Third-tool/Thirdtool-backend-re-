package com.Thethirdtool.backend.Member.domain;

import com.Thethirdtool.backend.Deck.domain.Deck;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String kakaoId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Deck> decks = new ArrayList<>();

    @Builder
    private Member(String kakaoId, String nickname, String email) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
        this.decks = new ArrayList<>();
    }

    public static Member of(String kakaoId, String nickname, String email) {
        return Member.builder()
                     .kakaoId(kakaoId)
                     .nickname(nickname)
                     .email(email)
                     .build();
    }
}
