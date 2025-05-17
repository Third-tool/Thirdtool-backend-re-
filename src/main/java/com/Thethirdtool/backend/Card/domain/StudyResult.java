package com.Thethirdtool.backend.Card.domain;

public enum StudyResult {
    AGAIN,      // 다시 학습 (failReview)-> fail로 치는 데
    NORMAL,     // 일반 복습 (restoreToGeneralStudy)
    GOOD,        // 성공 복습 (successReview)
    STAY         //  그냥 이대로 가자
}