package com.Thethirdtool.backend.Card.application;


import com.Thethirdtool.backend.Card.application.repository.CardRepository;
import com.Thethirdtool.backend.Deck.application.repository.DeckRepository;
import com.Thethirdtool.backend.Note.application.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final NoteRepository noteRepository;



}
