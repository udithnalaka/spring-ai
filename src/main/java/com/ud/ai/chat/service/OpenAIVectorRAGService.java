package com.ud.ai.chat.service;

import com.ud.ai.chat.dto.rag.Models;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class OpenAIVectorRAGService {

    private final ChatClient chatClient;

    public OpenAIVectorRAGService(OpenAiChatModel chatModel, VectorStore simpleVectorStore) {
        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(QuestionAnswerAdvisor.builder(simpleVectorStore).build())
                .build();
    }

    public Models faq(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(Models.class);
    }
}
