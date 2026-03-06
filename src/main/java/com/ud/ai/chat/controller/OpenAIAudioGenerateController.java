package com.ud.ai.chat.controller;

import com.ud.ai.chat.service.OpenAIAudioGenerateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openai/audio")
public class OpenAIAudioGenerateController {

    private final OpenAIAudioGenerateService aiAudioGenerateService;


    public OpenAIAudioGenerateController(OpenAIAudioGenerateService aiAudioGenerateService) {
        this.aiAudioGenerateService = aiAudioGenerateService;
    }

    @RequestMapping("/speech")
    public ResponseEntity<byte[]> speech(@RequestParam(defaultValue = "It's a great time to be a Java & Spring AI Engineer") String text) {

        byte[] speechInBytes = aiAudioGenerateService.generateSpeech(text);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"speech.mp3\"")
                .body(speechInBytes);
    }
}
