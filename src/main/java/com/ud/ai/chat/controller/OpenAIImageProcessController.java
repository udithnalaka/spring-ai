package com.ud.ai.chat.controller;

import com.ud.ai.chat.service.OpenAIImageGenerationService;
import com.ud.ai.chat.service.OpenAIImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/openai/image")
public class OpenAIImageProcessController {

    private final OpenAIImageService openAIImageService;
    private final OpenAIImageGenerationService openAIImageGenerationService;

    public OpenAIImageProcessController(OpenAIImageService openAIImageService,
                                        OpenAIImageGenerationService openAIImageGenerationService) {
        this.openAIImageService = openAIImageService;
        this.openAIImageGenerationService = openAIImageGenerationService;
    }

    /**
     * using ChatClient's user.media() to send an image to the LLM to read the image and respond.
     */
    @GetMapping("/read")
    public String describeImage() {

        return openAIImageService.imageToText();
    }

    /**
     * using OpenAiImageModel to generate an image according to the ImageOptions provided.
     */
    @GetMapping("/generate")
    public ResponseEntity<Map<String, String>> generateImage(
            @RequestParam(defaultValue = "a beautiful sunset over the ocean") String prompt) {
        String imageUrl = openAIImageGenerationService.generateImage(prompt);

        return ResponseEntity.ok(Map.of(
                "prompt", prompt,
                "imageUrl", imageUrl
        ));
    }
}
