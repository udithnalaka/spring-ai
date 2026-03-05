package com.ud.ai.chat.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class OpenAIImageService {

    private final ChatClient chatClient;

    @Value("classpath:/image1.png")
    Resource sampleImage;

    public OpenAIImageService(OpenAiChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    /**
     * Read an image and describe.
     * <pre>
     *    Response sample from OpenAI:
     *
     *     The image shows a scenic landscape with a large pyramid-like structure in the center,
     *     surrounded by greenery and trees. There are several colorful hot air balloons floating in the sky above the pyramid.
     *     In the background, there are hills or mountains under a clear blue sky. The lighting suggests it might be early morning or late afternoon,
     *     casting a warm glow over the scene. The overall atmosphere is peaceful and picturesque.
     * </pre>
     *
     * @return String
     */
    public String imageToText() {

        return chatClient.prompt()
                .user(u -> {
                    u.text("can you please describe what you see in the following image?");
                    u.media(MimeTypeUtils.IMAGE_PNG, sampleImage);
                })
                .call()
                .content();
    }
}
