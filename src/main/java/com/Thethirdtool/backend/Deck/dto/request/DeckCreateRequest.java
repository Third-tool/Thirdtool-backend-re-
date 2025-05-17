package com.Thethirdtool.backend.Deck.dto.request;


import jakarta.validation.constraints.NotBlank;

public record DeckCreateRequest(
        @NotBlank(message = "덱 이름은 필수입니다.")
        String name,

        Long parentId // 루트 덱이면 null
) {}