package com.ud.ai.chat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OllamaService {

    private static final Logger log = LoggerFactory.getLogger(OllamaService.class);

    private final ChatClient chatClient;

    public OllamaService(OllamaChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    public String getAnswer(String message) {

        ChatResponse chatResponse = chatClient
                .prompt(message)
                .call()
                .chatResponse();

        log.info("Ollama response completed using the AI model: {}.", chatResponse.getMetadata().getModel());

        return chatResponse.getResult().getOutput().getText();
    }

    /**
     * chatClient using stream() for async call to the ollama LLM.
     * for a large response, the user will be getting the responses one by one rather than waiting for the complete response
     * from Ollama before sending the response to the user.
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
