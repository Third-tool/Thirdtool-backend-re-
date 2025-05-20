package com.Thethirdtool.backend.Card.dto.request;

import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Deck.domain.Deck;
import com.Thethirdtool.backend.Note.domain.Note;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

public record CardCreateRequest(
        @NotNull(message = "노트 ID는 필수입니다.")
        Long noteId,

        @NotBlank(message = "카드 내용은 비워둘 수 없습니다.")
        String content,

        @Size(max = 10, message = "태그는 최대 10개까지 등록할 수 있습니다.")
        List<@NotBlank(message = "태그는 비워둘 수 없습니다.") String> tags,

        // ✅ 이미지 파일 리스트 추가
        @Size(max = 3, message = "이미지는 최대 3개까지 등록할 수 있습니다.")
        List<MultipartFile> images
) {

    public static CardCreateRequest of(Long noteId, String content, List<String> tags, List<MultipartFile> images) {
        return new CardCreateRequest(noteId, content, tags, images);
    }

    // Entity 변환 시에는 이미지 URL은 따로 설정
    public Card toEntity(Deck deck, Note note, List<String> imageUrls) {
        if (imageUrls != null && imageUrls.size() > 3) {
            throw new IllegalArgumentException("이미지는 최대 3개까지 등록할 수 있습니다.");
        }
        Card card= Card.builder()
                       .deck(deck)
                       .note(note)
                       .content(this.content)
                       .tags(this.tags)
                       .dueDate(Instant.now().plusSeconds(86400L)) // 기본 1일 후
                       .intervalDays(1)
                       .easeFactor(2500)
                       .successCount(0)
                       .reps(0)
                       .lapses(0)
                       .build();

        card.setImageUrls(imageUrls);
        return card;
    }
}