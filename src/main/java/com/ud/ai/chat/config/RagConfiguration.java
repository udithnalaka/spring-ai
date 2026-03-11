package com.ud.ai.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RagConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RagConfiguration.class);

    private final String vectorStoreName = "vectorstore.json";

    @Value("classpath:/data/models.json")
    private Resource models;

   @Bean
    public SimpleVectorStore simpleVectorStore(OpenAiEmbeddingModel embeddingModel) {

       SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
       var vectorStoreFile = getVectorStoreFile();
       if (vectorStoreFile.exists()) {
           log.info("Vector store already exists at {}", vectorStoreFile.getAbsolutePath());
           simpleVectorStore.load(vectorStoreFile);
       } else {
           log.info("Vector store not found. Creating Vector store at {}", vectorStoreFile.getAbsolutePath());
           TextReader textReader = new TextReader(models);
           textReader.getCustomMetadata().put("filename", "models.txt");

           List<Document> documents = textReader.get();
           TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
           List<Document> splitDocuments = tokenTextSplitter.apply(documents);

           simpleVectorStore.add(splitDocuments);
           simpleVectorStore.save(vectorStoreFile);
       }

       return simpleVectorStore;
   }

   private File getVectorStoreFile() {
       return Paths.get("src/main/resources/data", vectorStoreName)
               .toAbsolutePath()
               .toFile();
   }
}
