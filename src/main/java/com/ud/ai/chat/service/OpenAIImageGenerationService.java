package com.ud.ai.chat.service;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class OpenAIImageGenerationService {

    private final OpenAiImageModel imageModel;

    public OpenAIImageGenerationService(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    /**
     * generate an image from the prompt text provided and return the image URL.
     * @param prompt Image description
     * @return String image Url
     */
    public String generateImage(String prompt) {

        ImageOptions options = OpenAiImageOptions.builder()
                .model("dall-e-3")
                .width(1024)
                .height(1024)
                .quality("hd")
                .style("vivid")
                .build();

        var imagePrompt = new ImagePrompt(prompt, options);
        var imageResponse = imageModel.call(imagePrompt);

        return imageResponse.getResult().getOutput().getUrl();
    }
}
