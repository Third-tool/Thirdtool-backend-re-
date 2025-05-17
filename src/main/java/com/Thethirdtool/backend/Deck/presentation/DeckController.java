package com.Thethirdtool.backend.Deck.presentation;


import com.Thethirdtool.backend.Card.application.CardService;
import com.Thethirdtool.backend.Card.dto.response.ApiResponse;
import com.Thethirdtool.backend.Deck.application.DeckService;
import com.Thethirdtool.backend.Deck.domain.Deck;
import com.Thethirdtool.backend.Deck.dto.response.DeckResponse;
import com.Thethirdtool.backend.Image.application.ImageService;
import com.Thethirdtool.backend.security.CustomUserDetails;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

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



}
