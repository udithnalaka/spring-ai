package com.ud.ai.chat.controller;

import com.ud.ai.chat.service.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/openai")
public class OpenAIChatController {

    private final OpenAIService openAIService;

    public OpenAIChatController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @RequestMapping("/{message}")
    public ResponseEntity<String> getAnswer(@PathVariable String message) {

        String response = openAIService.getAnswer(message);

        return ResponseEntity.ok("Answer: " + response);
    }

    @RequestMapping("/stream")
    public Flux<String> getSuggestions() { // using ChatClient stream (asynchronous requests)

        String message = "I'm visiting Australia. can you tell me 2 places to visit?";

        return openAIService.getSuggestions(message);
    }

}
