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
