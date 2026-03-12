package com.ud.ai.chat.controller.tools;

import com.ud.ai.chat.service.OpenAIDateTimeToolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/openai/tools")
public class OpenAIDateTimeController {

    private final OpenAIDateTimeToolService openAIService;

    public OpenAIDateTimeController(OpenAIDateTimeToolService openAIService) {
        this.openAIService = openAIService;
    }


    /**
     * using ChatClient's tool() to response based on known information like date and time, weather.
     * e.g: DateTimeTool will inform the current date and time. based on that the LLM will pick tomorrow's date.
     */
    @GetMapping("/date-time")
    public String getDateTime() {

        return openAIService.getDateTime("What is tomorrow's date?");
    }

}
