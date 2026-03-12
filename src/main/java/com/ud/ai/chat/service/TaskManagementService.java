package com.ud.ai.chat.service;

import com.ud.ai.chat.tool.TaskManagementTool;
import com.ud.ai.chat.tool.TaskManagementTool.TaskResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class TaskManagementService {

    private final ChatClient chatClient;
    private final TaskManagementTool taskManagementTool;

    public TaskManagementService(OpenAiChatModel chatModel, TaskManagementTool taskManagementTool) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.taskManagementTool = taskManagementTool;
    }

    public TaskResult createTask(String message) {

        return chatClient.prompt()
                .user(message)
                .tools(taskManagementTool)
                .call()
                .entity(TaskResult.class);
    }
}
