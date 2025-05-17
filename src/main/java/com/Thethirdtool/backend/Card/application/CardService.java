package com.Thethirdtool.backend.Card.application;


import com.Thethirdtool.backend.Card.application.repository.CardRepository;
import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Deck.application.repository.DeckRepository;
import com.Thethirdtool.backend.Deck.domain.Deck;
import com.Thethirdtool.backend.Note.application.repository.NoteRepository;
import com.Thethirdtool.backend.Note.domain.Note;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final NoteRepository noteRepository;



    // ✅ 카드 생성
    @Transactional
    public Card createCard(Long deckId, Long noteId, CardCreateRequest request, List<String> imageUrls) {
        Deck deck = deckRepository.findById(deckId)
                                  .orElseThrow(() -> new EntityNotFoundException("덱이 존재하지 않습니다."));
        Note note = noteRepository.findById(noteId)
                                  .orElseThrow(() -> new EntityNotFoundException("노트를 찾을 수 없습니다."));

        Card card = Card.builder()
                        .deck(deck)
                        .note(note)
                        .content(request.content())
                        .tags(request.tags())
                        .dueDate(Instant.now().plusSeconds(86400L))
                        .intervalDays(1)
                        .easeFactor(2500)
                        .successCount(0)
                        .reps(0)
                        .lapses(0)
                        .build();
        card.setImageUrls(imageUrls != null ? imageUrls : new ArrayList<>());

        return cardRepository.save(card);
    }

    // ✅ 카드 삭제
    @Transactional
    public void deleteCard(Long cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new EntityNotFoundException("삭제할 카드가 존재하지 않습니다.");
        }
        cardRepository.deleteById(cardId);
    }



}
