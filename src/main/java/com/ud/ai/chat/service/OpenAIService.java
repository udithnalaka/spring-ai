package com.ud.ai.chat.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OpenAIService {

    private final ChatClient chatClient;

    public OpenAIService(OpenAiChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }


    public String getAnswer(String message) {

        //simple call to the model
        return chatClient
                .prompt(message)
                .call()
                .content();

    }

    /**
     * chatClient using stream() for async call to the OpenAI LLM.
     * for a large response, the user will be getting the responses one by one rather than waiting for the complete response
     * from OpenAI before sending the response to the user.
     * @param message
     * @return Flux<String>
     */
    public Flux<String> getSuggestions(String message) {

        return chatClient
                .prompt(message)
                .stream()
                .content();
    }
}
