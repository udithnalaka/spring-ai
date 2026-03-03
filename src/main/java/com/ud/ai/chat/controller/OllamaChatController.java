package com.ud.ai.chat.controller;

import com.ud.ai.chat.service.OllamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api/v1/ollama")
public class OllamaChatController {

    private static final Logger log = LoggerFactory.getLogger(OllamaChatController.class);

    private final OllamaService ollamaService;

    public OllamaChatController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @RequestMapping("/{message}")
    public ResponseEntity<String> getAnswer(@PathVariable String message) {

        log.info("Hey, Ollama AI model, give me the answer for message= {}", message);

        String response = ollamaService.getAnswer(message);

        return ResponseEntity.ok("Answer: " + response);
    }

    @RequestMapping("/stream")
    public Flux<String> getSuggestions() { // using ChatClient stream (asynchronous requests)

        String message = "I'm visiting Japan. can you tell me 2 places to visit in Japan?";
        log.info("get suggestion using async call for message: {}.", message);

        return ollamaService.getSuggestions(message);
    }
}
