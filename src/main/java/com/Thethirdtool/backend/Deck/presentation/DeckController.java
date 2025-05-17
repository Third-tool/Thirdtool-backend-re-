package com.Thethirdtool.backend.Deck.presentation;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/{userId}/decks")
public class DeckController {
}
