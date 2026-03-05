package com.ud.ai.chat.controller;

import com.ud.ai.chat.dto.Itinerary;
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

    /**
     * To get a structured response (in JSON format) for a given request text.
     *  <pre>
     * Request: I want to plan a trip to Hawaii. Give me a list of things to do.
     *
     * Response:
     *
     *    {"itinerary":[
     *       {
     *          "activity":"Visit Waikiki Beach",
     *          "location":"Oahu",
     *          "day":"Monday",
     *          "time":"09:00 AM"
     *       },
     *       {
     *          "activity":"Hike Diamond Head",
     *          "location":"Oahu",
     *          "day":"Tuesday",
     *          "time":"07:00 AM"
     *       },
     *       {
     *          "activity":"Explore Pearl Harbor National Memorial",
     *          "location":"Oahu",
     *          "day":"Wednesday",
     *          "time":"10:00 AM"
     *       }
     *       ]}
     *    </pre>
     */
    @GetMapping("/vacation/structured")
    public Itinerary vacationStructured() {

        return openAIService.vacationDetailsStructured();
    }

}
