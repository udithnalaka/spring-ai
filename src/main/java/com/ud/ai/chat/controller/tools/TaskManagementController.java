package com.ud.ai.chat.controller.tools;

import com.ud.ai.chat.service.TaskManagementService;
import com.ud.ai.chat.tool.TaskManagementTool.TaskResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openai/task")
public class TaskManagementController {

    private final TaskManagementService taskService;

    public TaskManagementController(TaskManagementService taskService) {
        this.taskService = taskService;
    }


    /**
     * using ChatClient's tool() to create Task based on available information passed to the LLM.
     * TaskManagementTool class is used to service the user request.
     */
    @GetMapping("/create")
    public TaskResult createTask(@RequestParam String message) {

        return taskService.createTask(message);
    }

}
