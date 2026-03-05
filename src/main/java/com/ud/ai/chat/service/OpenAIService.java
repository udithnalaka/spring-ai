package com.ud.ai.chat.service;

import com.ud.ai.chat.dto.Itinerary;
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

    /**
     * ChatClient using a system instruction message to only respond to the banking related questions.
     * Or else, send a default message back.
     *
     * @param message to ask from the agent
     * @return String
     */
    public String getBankingChatResponse(String message) {

        var systemInstructions = """
                You are a customer service assistant for MyBank.
                You can ONLY discuss:
                - Accout Balance and transactions
                - Branch location and hours
                - General banking services
                
                If asked anything else, respond: "I can only help with banking related questions."
                """;

        return chatClient.prompt()
                .user(message)
                .system(systemInstructions)
                .call()
                .content();
    }

    public String createBlogPost(String topic) {

        // use it as a guide or a restriction to the model's behaviour.
        var systemInstruction = """
                Blog Post Generation Guidelines:
                
                1. Length & Purpose: Generate a 200 word blog post that informs and engage school kids as audience.
                
                2. Structure:
                    - Introduction: Hook readers and establish the topic's relevance.
                    - Body: Develop a main point with supporting evidence and example.
                    - Conclusion: summarize key takeaways.
                    
                3. Content requirements:
                    - Include real world examples.
                    - explain clearly for non expert audience.
                    
                4. Tone & Style: 
                    - Write in an informational yet conversational voice.
                    - use simple english language. 
                    
                5. Response format: Delivery complete ready to publish post with a suggested title.
                """;

        return chatClient.prompt()
                .user(u -> {
                    u.text("Write me a blog post about {topic}");
                    u.param("topic", topic);
                })
                .system(systemInstruction)
                .call()
                .content();
    }

    public Itinerary vacationDetailsStructured() {

        return chatClient.prompt()
                .user("I want to plan a trip to Hawaii. Give me a list of things to do.")
                .call()
                .entity(Itinerary.class);
    }
}
