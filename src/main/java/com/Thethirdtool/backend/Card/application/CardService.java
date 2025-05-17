package com.Thethirdtool.backend.Card.application;


import com.Thethirdtool.backend.Card.application.repository.CardRepository;
import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Card.domain.DuePeriod;
import com.Thethirdtool.backend.Card.domain.StudyPolicy.CardBehavior;
import com.Thethirdtool.backend.Card.domain.StudyPolicy.CardBehaviorFactory;
import com.Thethirdtool.backend.Card.domain.StudyResult;
import com.Thethirdtool.backend.Card.dto.CardStudyViewDto;
import com.Thethirdtool.backend.Card.dto.request.CardCreateRequest;
import com.Thethirdtool.backend.Card.dto.request.CardUpdateRequest;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final NoteRepository noteRepository;


    //카드 단건 조회- 스크롤링 방식- 근데 할 일이 없을거 같음
    @Transactional(readOnly = true)
    public CardStudyViewDto getCardStudyView(Long cardId) {
        Card card = cardRepository.findById(cardId)
                                  .orElseThrow(() -> new EntityNotFoundException("카드를 찾을 수 없습니다."));
        return CardStudyViewDto.from(card);
    }


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

    //수정하기
    @Transactional
    public Card updateCard(Long cardId, CardUpdateRequest request) {
        Card card = cardRepository.findById(cardId)
                                  .orElseThrow(() -> new EntityNotFoundException("카드를 찾을 수 없습니다."));

        if (request.deckId() != null) {
            Deck deck = deckRepository.findById(request.deckId())
                                      .orElseThrow(() -> new EntityNotFoundException("덱이 존재하지 않습니다."));
            card.updateDeck(deck);
        }

        if (request.noteId() != null) {
            Note note = noteRepository.findById(request.noteId())
                                      .orElseThrow(() -> new EntityNotFoundException("노트를 찾을 수 없습니다."));
            card.updateNote(note);
        }

        if (request.content() != null && !request.content().isBlank()) {
            card.updateContent(request.content());
        }

        return card;
    }
    //CardBehavior로 ->> 3day 중에서 선택하게 만들었다.
    @Transactional
    public void processStudyResult(Long cardId, StudyResult result) {
        Card card = cardRepository.findById(cardId)
                                  .orElseThrow(() -> new EntityNotFoundException("카드를 찾을 수 없습니다."));
        CardBehavior behavior = CardBehaviorFactory.from(card);
        behavior.processStudyResult(card, result);
    }

    // 3day 일 경우 -> stay, 3day, 1week, 2week 중 고르게한다.
    @Transactional
    public void processDueGroupSelection(Long cardId, String selectedGroup) {
        Card card = cardRepository.findById(cardId)
                                  .orElseThrow(() -> new EntityNotFoundException("카드를 찾을 수 없습니다."));
        CardBehavior behavior = CardBehaviorFactory.from(card);
        behavior.resetDueGroup(card, selectedGroup);
    }

    //3day 프로젝트 중 3day,1week,2week 필터링 서비스
    @Transactional
    public List<Card> getCardsByDuePeriod(Long deckId, String period) {
        Deck deck = deckRepository.findById(deckId)
                                  .orElseThrow(() -> new EntityNotFoundException("덱이 존재하지 않습니다."));

        DuePeriod range = DuePeriod.fromString(period);

        return deck.getCards().stream()
                   .filter(card -> card.isInDueRangeByCreatedAt(range.min(), range.max()))
                   .sorted(Comparator.comparing(Card::getDueDate))
                   .toList();
    }
    //리포지토리에서 덱들 중 3day에 해당하는 archived가 False인 값인 카드들을 가져온다.
    public List<Card> getCardsFor3DayProject(Long deckId) {
        return cardRepository.findByDeckIdAndIsArchivedFalseAndIntervalDaysLessThanEqual(deckId, 30);
    }
    //리포지토리에서 덱들 중 permanent에 해당하는 archived가 True인 값들만 가져온다.
    public List<Card> getCardsForPermanentProject(Long deckId) {
        return cardRepository.findByDeckIdAndIsArchivedTrue(deckId);
    }

    //preview 데이터 보내기 - 며칠 남았는지
    public Map<String, String> getStudyPreview(Long cardId) {
        Card card = cardRepository.findById(cardId)
                                  .orElseThrow(() -> new EntityNotFoundException("카드를 찾을 수 없습니다."));
        CardBehavior behavior = CardBehaviorFactory.from(card);
        return behavior.previewIntervals(card);
    }




}
