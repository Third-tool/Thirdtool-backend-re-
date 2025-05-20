package com.Thethirdtool.backend.Deck.presentation;


import com.Thethirdtool.backend.Card.application.CardService;
import com.Thethirdtool.backend.Card.domain.Card;
import com.Thethirdtool.backend.Card.dto.request.CardCreateRequest;
import com.Thethirdtool.backend.Card.dto.response.ApiResponse;
import com.Thethirdtool.backend.Card.dto.response.CardResponse;
import com.Thethirdtool.backend.Deck.application.DeckService;
import com.Thethirdtool.backend.Deck.domain.Deck;
import com.Thethirdtool.backend.Deck.dto.request.DeckCreateRequest;
import com.Thethirdtool.backend.Deck.dto.response.DeckResponse;
import com.Thethirdtool.backend.Image.application.ImageService;
import com.Thethirdtool.backend.common.validation.AuthValidationDto;
import com.Thethirdtool.backend.security.CustomOAuth2User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
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
            @AuthenticationPrincipal CustomOAuth2User userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        List<Deck> decks = deckService.getRootDecks();
        return ResponseEntity.ok(ApiResponse.ok(decks.stream().map(DeckResponse::from).toList()));
    }

    //자식 덱 가져오기
    @GetMapping("/{deckId}/children")
    public ResponseEntity<ApiResponse<List<DeckResponse>>> getChildren(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomOAuth2User userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        List<Deck> children = deckService.getImmediateChildren(deckId);
        return ResponseEntity.ok(ApiResponse.ok(children.stream().map(DeckResponse::from).toList()));
    }

    @PatchMapping("/{deckId}/freeze")
    public ResponseEntity<ApiResponse<String>> freezeDeck(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomOAuth2User userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        deckService.freezeDeck(deckId);
        return ResponseEntity.ok(ApiResponse.ok("성공적으로 얼렸습니다."));
    }

    @PatchMapping("/{deckId}/unfreeze")
    public ResponseEntity<ApiResponse<String>> unfreezeDeck(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomOAuth2User userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        deckService.unfreezeDeck(deckId);
        return ResponseEntity.ok(ApiResponse.ok("성공적으로 녹였습니다."));
    }

    @GetMapping("/{deckId}/cards/3day")
    public ResponseEntity<ApiResponse<List<CardResponse>>> getCardsFor3Day(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomOAuth2User userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        List<Card> cards = deckService.getCardsFor3DayProject(deckId);
        return ResponseEntity.ok(ApiResponse.ok(cards.stream().map(CardResponse::from).toList()));
    }

    @GetMapping("/{deckId}/cards/permanent")
    public ResponseEntity<ApiResponse<List<CardResponse>>> getCardsForPermanent(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal CustomOAuth2User userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        List<Card> cards = deckService.getArchivedCardsFromDeckTree(deckId);
        return ResponseEntity.ok(ApiResponse.ok(cards.stream().map(CardResponse::from).toList()));
    }

    @GetMapping("/{deckId}/cards/due")
    public ResponseEntity<ApiResponse<List<CardResponse>>> getDueCardsByPeriod(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @RequestParam String period,
            @AuthenticationPrincipal CustomOAuth2User userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        List<Card> cards = cardService.getCardsByDuePeriod(deckId, period);
        return ResponseEntity.ok(ApiResponse.ok(cards.stream().map(CardResponse::from).toList()));
    }

    @PostMapping("/{parentId}/children")
    public ResponseEntity<ApiResponse<DeckResponse>> createChildDeck(
            @PathVariable Long userId,
            @PathVariable Long parentId,
            @RequestBody @Valid DeckCreateRequest request,
            @AuthenticationPrincipal CustomOAuth2User userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        Deck child = deckService.createChildDeck(parentId, request.name());
        return ResponseEntity.ok(ApiResponse.ok(DeckResponse.from(child)));
    }

    @PostMapping(value = "/{deckId}/cards", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<CardResponse>> createCard(
            @PathVariable Long userId,
            @PathVariable Long deckId,
            @RequestPart("data") @Valid CardCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @AuthenticationPrincipal CustomOAuth2User userDetails) throws IOException, AccessDeniedException {
        validateUser(userId, userDetails);

        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
                String url = imageService.imageUpload(image);
                imageUrls.add(url);
            }
        }

        Card card = cardService.createCard(deckId, request.noteId(), request, imageUrls);
        return ResponseEntity.ok(ApiResponse.ok(CardResponse.from(card)));
    }



    // ✅ 공통 인증 검증
    private void validateUser(Long userId, CustomOAuth2User userDetails) throws AccessDeniedException {
        AuthValidationDto dto = new AuthValidationDto(userId, userDetails.getMember().getId());
        Set<ConstraintViolation<AuthValidationDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new AccessDeniedException(violations.iterator().next().getMessage());
        }
    }



}
