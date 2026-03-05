package com.ud.ai.chat.controller;

import com.ud.ai.chat.service.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/openai")
public class OpenAIChatController {

    private final OpenAIService openAIService;

    public OpenAIChatController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    /**
     * a simple message sent to the LLM
     */
    @RequestMapping("/{message}")
    public ResponseEntity<String> getAnswer(@PathVariable String message) {

        String response = openAIService.getAnswer(message);

        return ResponseEntity.ok("Answer: " + response);
    }

    /**
     * using ChatClient stream (asynchronous requests) and returning a Flux response.
     */
    @RequestMapping("/stream")
    public Flux<String> getSuggestions() { //

        String message = "I'm visiting Australia. can you tell me 2 places to visit?";

        return openAIService.getSuggestions(message);
    }

    /**
     * using ChatClient's system() property to instruct the LLM to filter the question only for banking purposes.
     */
    @GetMapping("/chat")
    public String bankingChatAgent(@RequestParam String message) {

        return openAIService.getBankingChatResponse(message);

    }

    /**
     * using ChatClient's system() property to instruct the LLM to create a blog post as instructed.
     */
    @GetMapping("/blog")
    public String blogPost(@RequestParam(value = "topic", defaultValue = "JDK Virtual Threads") String topic) {

        return openAIService.createBlogPost(topic);

    }

}
