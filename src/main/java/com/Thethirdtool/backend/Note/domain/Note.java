package com.Thethirdtool.backend.Note.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String question; // 문제

    @Column(nullable = false, length = 1000)
    private String answer; // 정답

    private Instant mtime;

    private int usn;

    @Builder
    private Note(String question, String answer, Instant mtime, int usn) {
        this.question = question;
        this.answer = answer;
        this.mtime = mtime;
        this.usn = usn;
    }

    public static Note of(String question, String answer, Instant mtime, int usn) {
        return Note.builder()
                   .question(question)
                   .answer(answer)
                   .mtime(mtime)
                   .usn(usn)
                   .build();
    }
}