package com.Thethirdtool.backend.Card.domain;

public enum DuePeriod {
    D3(0, 3),
    WEEK1(4, 7),
    WEEK2(8, 14),
    MONTH1(15, 30);

    private final int min;
    private final int max;

    DuePeriod(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int min() { return min; }
    public int max() { return max; }

    public static DuePeriod fromString(String period) {
        return switch (period) {
            case "3day" -> D3;
            case "1week" -> WEEK1;
            case "2week" -> WEEK2;
            case "1month" -> MONTH1;
            default -> throw new IllegalArgumentException("잘못된 기간 선택입니다.");
        };
    }
}
