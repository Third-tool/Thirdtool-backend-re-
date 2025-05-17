package com.Thethirdtool.backend.Member.domain;

import com.Thethirdtool.backend.Deck.domain.Deck;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Builder
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    // 덱 연관관계 (옵션)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Deck> decks = new ArrayList<>();



}
