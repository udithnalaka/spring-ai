package com.ud.ai.chat.service;

import com.ud.ai.chat.tool.DateTimeTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class OpenAIDateTimeToolService {

    private final ChatClient chatClient;
    private final DateTimeTool dateTimeTool;

    public OpenAIDateTimeToolService(OpenAiChatModel chatModel,  DateTimeTool dateTimeTool) {
        this.chatClient = ChatClient.create(chatModel);
        this.dateTimeTool = dateTimeTool;
    }

    public String getDateTime(String message) {

        return chatClient.prompt()
                .user(message)
                .tools(dateTimeTool)
                .call()
                .content();
    }
}
