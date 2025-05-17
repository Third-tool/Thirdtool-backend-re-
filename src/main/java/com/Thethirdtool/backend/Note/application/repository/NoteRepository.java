package com.Thethirdtool.backend.Note.application.repository;

import com.Thethirdtool.backend.Note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,Long> {
}
