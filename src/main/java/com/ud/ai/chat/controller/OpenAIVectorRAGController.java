package com.ud.ai.chat.controller;

import com.ud.ai.chat.dto.rag.Models;
import com.ud.ai.chat.service.OpenAIVectorRAGService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openai/rag")
public class OpenAIVectorRAGController {

    private final OpenAIVectorRAGService openAIService;

    public OpenAIVectorRAGController(OpenAIVectorRAGService openAIService) {
        this.openAIService = openAIService;
    }


    @GetMapping("/models")
    public Models faq(@RequestParam(value = "message",
            defaultValue = "Give me a list of all the models from OpenAI along with their context window.") String message) {

        return openAIService.faq(message);
    }

}
