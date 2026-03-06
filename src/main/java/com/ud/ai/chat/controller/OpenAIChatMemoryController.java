package com.ud.ai.chat.controller;

import com.ud.ai.chat.service.OpenAIChatMemoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openai/memory")
public class OpenAIChatMemoryController {

    private final OpenAIChatMemoryService chatMemoryService;


    public OpenAIChatMemoryController(OpenAIChatMemoryService chatMemoryService) {
        this.chatMemoryService = chatMemoryService;
    }

    /**
     * using ChatClient's advisor() to keep the chat messages in memory using ChatMemory interface.
     */
    @RequestMapping("/chat")
    public String chatMemory(@RequestParam String message) {

        return chatMemoryService.chatMemory(message);
    }
}
