package com.ud.ai.chat.service;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    private final OpenAiChatModel chatModel;

    public OpenAIService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getAnswer(String message) {

        //simple call to the model
        return chatModel.call(message);

    }
}
