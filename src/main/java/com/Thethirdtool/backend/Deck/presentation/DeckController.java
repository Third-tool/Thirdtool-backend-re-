package com.Thethirdtool.backend.Deck.presentation;


import com.Thethirdtool.backend.Card.application.CardService;
import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Card.dto.response.ApiResponse;
import com.Thethirdtool.backend.Card.dto.response.CardResponse;
import com.Thethirdtool.backend.Deck.application.DeckService;
import com.Thethirdtool.backend.Deck.domain.Deck;
import com.Thethirdtool.backend.Deck.dto.response.DeckResponse;
import com.Thethirdtool.backend.Image.application.ImageService;
import com.Thethirdtool.backend.common.validation.AuthValidationDto;
import com.Thethirdtool.backend.security.CustomUserDetails;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/{userId}/decks")
public class DeckController {

    private final DeckService deckService;
    private final CardService cardService;
    private final ImageService imageService;
    private final Validator validator;

    @GetMapping("/roots")
    public ResponseEntity<ApiResponse<List<DeckResponse>>> getRootDecks(
            @PathVariable Long userId,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        List<Deck> decks = deckService.getRootDecks();
        return ResponseEntity.ok(ApiResponse.ok(decks.stream().map(DeckResponse::from).toList()));
    }

    //자식 덱 가져오기
    @GetMapping("/{deckId}/children")
    public ResponseEntity<ApiResponse<List<DeckResponse>>> getChildren(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        List<Deck> children = deckService.getImmediateChildren(deckId);
        return ResponseEntity.ok(ApiResponse.ok(children.stream().map(DeckResponse::from).toList()));
    }

    @PatchMapping("/{deckId}/freeze")
    public ResponseEntity<ApiResponse<String>> freezeDeck(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        deckService.freezeDeck(deckId);
        return ResponseEntity.ok(ApiResponse.ok("성공적으로 얼렸습니다."));
    }

    @PatchMapping("/{deckId}/unfreeze")
    public ResponseEntity<ApiResponse<String>> unfreezeDeck(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        deckService.unfreezeDeck(deckId);
        return ResponseEntity.ok(ApiResponse.ok("성공적으로 녹였습니다."));
    }

    @GetMapping("/{deckId}/cards/3day")
    public ResponseEntity<ApiResponse<List<CardResponse>>> getCardsFor3Day(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        List<Card> cards = deckService.getCardsFor3DayProject(deckId);
        return ResponseEntity.ok(ApiResponse.ok(cards.stream().map(CardResponse::from).toList()));
    }

    @GetMapping("/{deckId}/cards/permanent")
    public ResponseEntity<ApiResponse<List<CardResponse>>> getCardsForPermanent(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        List<Card> cards = deckService.getArchivedCardsFromDeckTree(deckId);
        return ResponseEntity.ok(ApiResponse.ok(cards.stream().map(CardResponse::from).toList()));
    }



    // ✅ 공통 인증 검증
    private void validateUser(Long userId, CustomUserDetails userDetails) throws AccessDeniedException {
        AuthValidationDto dto = new AuthValidationDto(userId, userDetails.getMember().getId());
        Set<ConstraintViolation<AuthValidationDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new AccessDeniedException(violations.iterator().next().getMessage());
        }
    }



}
