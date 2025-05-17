package com.Thethirdtool.backend.Deck.presentation;


import com.Thethirdtool.backend.Card.application.CardService;
import com.Thethirdtool.backend.Deck.application.DeckService;
import com.Thethirdtool.backend.Deck.domain.Deck;
import com.Thethirdtool.backend.Deck.dto.response.DeckResponse;
import com.Thethirdtool.backend.Image.application.ImageService;
import com.Thethirdtool.backend.security.CustomUserDetails;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
