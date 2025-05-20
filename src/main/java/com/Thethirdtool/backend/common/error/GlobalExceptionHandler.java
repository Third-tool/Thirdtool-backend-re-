package com.Thethirdtool.backend.common.error;

import com.Thethirdtool.backend.Card.dto.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // DTO 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleValidationError(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .findFirst()
                                .orElse("유효성 검사에 실패했습니다.");

        return ApiResponse.error(errorMessage);
    }

    // 단일 필드 제약조건 위반 (예: @RequestParam 등)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleConstraintViolation(ConstraintViolationException ex) {
        return ApiResponse.error("잘못된 요청: " + ex.getMessage());
    }

    // 엔티티를 찾지 못했을 경우
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<?> handleEntityNotFound(EntityNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleGenericException(Exception ex) {
        return ApiResponse.error("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
    }
}