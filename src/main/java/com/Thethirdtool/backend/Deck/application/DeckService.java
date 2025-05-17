package com.Thethirdtool.backend.Deck.application;


import com.Thethirdtool.backend.Card.application.repository.CardRepository;
import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Deck.application.repository.DeckRepository;
import com.Thethirdtool.backend.Deck.domain.Deck;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor // Builder를 위해 필요
@Transactional
@RequiredArgsConstructor
@Service
public class DeckService {
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;

    // 최상위 덱 리스트 (3day 프로젝트)
    public List<Deck> getRootDecks() {
        return deckRepository.findByParentIsNull();
    }

    // ✅ isFrozen = false인 덱의 카드만 가져오기 (3day용)
    public List<Card> getCardsFor3DayProject(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                                  .orElseThrow(() -> new EntityNotFoundException("덱을 찾을 수 없습니다."));

        // 얼린 덱이면 빈 리스트 반환
        if (deck.isFrozen()) {
            return List.of();
        }

        return cardRepository.findByDeckIdAndIsArchivedFalseAndIntervalDaysLessThanEqual(deckId, 30);
    }

    // ✅ 영구 프로젝트용 카드 조회: 하위 덱 중 얼리지 않은 것만 순회
    public List<Card> getArchivedCardsFromDeckTree(Long rootDeckId) {
        Deck root = deckRepository.findById(rootDeckId)
                                  .orElseThrow(() -> new EntityNotFoundException("덱을 찾을 수 없습니다."));

        List<Deck> deckTree = getDeckTree(root);
        List<Card> result = new ArrayList<>();

        for (Deck d : deckTree) {
            if (!d.isFrozen()) {
                result.addAll(cardRepository.findByDeckIdAndIsArchivedTrue(d.getId()));
            }
        }

        return result;
    }

    /**
     * 덱의 직접적인 children 덱 리스트만 반환 (➕ 버튼 클릭 시)
     */
    public List<Deck> getImmediateChildren(Long deckId) {
        Deck parent = deckRepository.findById(deckId)
                                    .orElseThrow(() -> new EntityNotFoundException("해당 덱을 찾을 수 없습니다."));

        return parent.getChildren()
                     .stream()
                     .filter(child -> !child.isFrozen()) // 얼리지 않은 것만
                     .toList();
    }


    @Transactional
    public void freezeDeck(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                                  .orElseThrow(() -> new IllegalArgumentException("Deck not found"));
        deck.freeze();
    }

    @Transactional
    public void unfreezeDeck(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                                  .orElseThrow(() -> new IllegalArgumentException("Deck not found"));
        deck.unfreeze();
    }
    //하위 덱 만들기
    @Transactional
    public Deck createChildDeck(Long parentId, String name) {
        Deck parent = deckRepository.findById(parentId)
                                    .orElseThrow(() -> new EntityNotFoundException("부모 덱이 존재하지 않습니다."));

        Deck child = Deck.builder()
                         .name(name)
                         .parent(parent)
                         .isFrozen(false)
                         .build();

        return deckRepository.save(child);
    }


    // ✅ 얼린 덱은 탐색 대상에서 제외->>> 이거는 사상으로는 무조건 도메인에서 처리해야함
    private List<Deck> getDeckTree(Deck root) {
        List<Deck> result = new ArrayList<>();

        if (root.isFrozen()) {
            return result; // 얼린 덱은 아예 무시
        }

        result.add(root);

        for (Deck child : root.getChildren()) {
            result.addAll(getDeckTree(child)); // 재귀 호출
        }

        return result;
    }
}
