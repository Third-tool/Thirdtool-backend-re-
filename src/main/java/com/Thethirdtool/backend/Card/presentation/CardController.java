package com.Thethirdtool.backend.Card.presentation;

import com.Thethirdtool.backend.Card.application.CardService;
import com.Thethirdtool.backend.Card.dto.CardStudyViewDto;
import com.Thethirdtool.backend.Card.dto.request.CardStudyResultRequest;
import com.Thethirdtool.backend.Card.dto.request.CardUpdateRequest;
import com.Thethirdtool.backend.Card.dto.response.ApiResponse;
import com.Thethirdtool.backend.Card.dto.response.CardResponse;
import com.Thethirdtool.backend.common.validation.AuthValidationDto;
import com.Thethirdtool.backend.security.CustomUserDetails;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/members/{userId}/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final Validator validator;

    // 카드 수정
    @PatchMapping("/{cardId}")
    public ResponseEntity<ApiResponse<CardResponse>> updateCard(
            @PathVariable Long userId,
            @PathVariable Long cardId,
            @Valid @RequestBody CardUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        return ResponseEntity.ok(ApiResponse.ok(CardResponse.from(cardService.updateCard(cardId, request))));
    }

    // 카드 삭제
    @DeleteMapping("/{cardId}")
    public ResponseEntity<ApiResponse<String>> deleteCard(
            @PathVariable Long userId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        cardService.deleteCard(cardId);
        return ResponseEntity.ok(ApiResponse.ok("성공적으로 삭제하였습니다."));
    }


    // 카드 학습 진입 (단일 선택 시)
    @GetMapping("/{cardId}/study")
    public ResponseEntity<ApiResponse<CardStudyViewDto>> getStudyCard(
            @PathVariable Long userId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        return ResponseEntity.ok(ApiResponse.ok(cardService.getCardStudyView(cardId)));
    }

    // 학습 전 프리뷰
    @GetMapping("/{cardId}/study/preview")
    public ResponseEntity<ApiResponse<Map<String, String>>> getStudyPreview(
            @PathVariable Long userId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        return ResponseEntity.ok(ApiResponse.ok(cardService.getStudyPreview(cardId)));
    }

    // 학습 결과 전송
    @PostMapping("/{cardId}/study/result")
    public ResponseEntity<ApiResponse<String>> studyResult(
            @PathVariable Long userId,
            @PathVariable Long cardId,
            @Valid @RequestBody CardStudyResultRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateUser(userId, userDetails);
        cardService.processStudyResult(cardId, request.result());
        return ResponseEntity.ok(ApiResponse.ok("보내는데 성공하였습니다."));
    }



    // ✅ 공통 검증 메서드 ->>> 공통메서드 뽑을 줄 알기
    private void validateUser(Long userId, CustomUserDetails userDetails) throws AccessDeniedException {
        AuthValidationDto dto = new AuthValidationDto(userId, userDetails.getMember().getId());
        Set<ConstraintViolation<AuthValidationDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new AccessDeniedException(violations.iterator().next().getMessage());
        }
    }

}
