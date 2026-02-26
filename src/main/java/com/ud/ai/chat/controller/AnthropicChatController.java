package com.ud.ai.chat.controller;

import com.ud.ai.chat.service.AnthropicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/anthropic")
public class AnthropicChatController {

    private final AnthropicService anthropicService;

    public AnthropicChatController(AnthropicService anthropicService) {
        this.anthropicService = anthropicService;
    }

    @RequestMapping("/{message}")
    public ResponseEntity<String> getAnswer(@PathVariable String message) {

        String response = anthropicService.getAnswer(message);

        return ResponseEntity.ok("Answer: " + response);
    }
}
