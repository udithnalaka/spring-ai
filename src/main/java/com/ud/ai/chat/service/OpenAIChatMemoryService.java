package com.ud.ai.chat.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class OpenAIChatMemoryService {

    private final ChatClient chatClient;

    public OpenAIChatMemoryService(OpenAiChatModel chatModel, ChatMemory chatMemory) {
        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    /**
     * Memorize the messages sent between the user and the OpenAI model.
     * <pre>
     *     example:
     *       First request to LLM: My name is Udith.
     *       Second request to LLM: What is my name?
     *          Response from LLM: Your name is Udith.
     * </pre>
     *
     * @param message
     * @return response from OpenAI
     */
    public String chatMemory(String message) {

        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
