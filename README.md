# Getting Started with Spring AI

### Tools, Frameworks used
* Java 21
* Springboot 3.5
* Spring AI (v 1.1.2)

### AI Functions implemented
Ollama LLM (running locally)
* running Ollama LLM locally to do simple message queries.

OpenAI LLM
* simple text search.
* ChatClient stream (asynchronous requests) and returning a Flux response.
* chatClient's system() property to instruct the LLM to filter the question only for banking purposes.
* ChatClient's system() property to instruct the LLM to create a blog post as instructed.
* ChatClient's entity() to structure the response according to the Object structure we want.
* ChatClient's advisor() to keep the chat messages in memory using ChatMemory interface.
* OpenAiAudioSpeechModel to convert text to speech.
* ChatClient's user.media() to send an image to the LLM to read the image and describe the image.
* OpenAiImageModel to generate an image according to the ImageOptions provided.
* RAG (Retrieval-Augmented Generation). VectorStore and ChatClient's QuestionAnswerAdvisor() to respond based on the context in VectorStore.
* ChatClient's tool() to respond based on known information like current date and time.

### Simple chat application using the following AI models

* Anthropic - Claude (paid LLM)
* OpenAI - ChatGPT (paid LLM)
* Ollama: Free AI model running locally. installation steps mentioned below.

**AnthropicChatController:**
    uses the  **AnthropicChatModel** to connect to their servers to retrieve answers for the given message.

**OpenAIChatController:**
    uses the **OpenAiChatModel** to connect to their servers to retrieve answers for the given message.

**OllamaChatController**
    uses the **OllamaChatModel** to connect to the AI model running locally. There is no cost when using this model, 
    but the data is limited depending on the AI model running in local machine. 

### Ollama (https://ollama.com/)
  Free tool that allows to use open models to build AI powered application. Has hundreds of AI models like OpenAI, Anthropic, Deepseek, etc
  that can be downloaded to your local machine and integrate with your application.

below are the steps used to integrate Ollama with this Spring boot AI application.

* download Ollama from the above mentioned website and install the tool.

* search for a model that suits your for your purpose from https://ollama.com/search
  (some models are very large and will take lot of resources to run internally. check for the size and use accordingly.
  To have a good dataset, better to have a large model).

* some commands that can be used from command line
    
        ollama list
        ollama run <model_name>

    ![img.png](img.png)

* In your springboot app, add the following to your application.yml file. (no need to run to manually in command prompt)
        
        spring:  
          ai:
            ollama:
              chat:
                options:
                  model: llama3.2:latest

* add the following to the pom file (add other models as required)
        
        dependencies section:
          <dependency>
              <groupId>org.springframework.ai</groupId>
              <artifactId>spring-ai-starter-model-ollama</artifactId>
          </dependency>
        
        dependencyManagement section:
            <dependency>
                <groupId>org.springframework.ai</groupId>
                <artifactId>spring-ai-bom</artifactId>
                <version>${spring-ai.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

* add the **ChatClient** and use the client to send a prompt to the AI model as mentioned in the service classes in this project.

* run the application.

      mvn spring-boot:run

* send a http request to the controller endpoints

      curl http://localhost:8080/api/v1/ollama/Java
* spring will initialize the ollama AI model and send the request message (in this case 'Java') as a prompt to get the answer.

  ![img_1.png](img_1.png)

**Note**: Anthropic (Claude) and OpenAI (ChatGPT) are paid LLM agents and will not give a response if no proper api-key and credits available.

### Troubleshooting

* If the api-key is not properly configured to an AI model

      org.springframework.ai.retry.NonTransientAiException: HTTP 401 - {
        "error": {
          "message": "Incorrect API key provided: sk-proj-********xxxx. You can find your API key at https://platform.openai.com/account/api-keys.",
          "type": "invalid_request_error",
          "code": "invalid_api_key",
          "param": null
        },
      "status": 401
      }

* If api-key is correct, but have no credit to cater the request (openai)

      org.springframework.ai.retry.NonTransientAiException: HTTP 429 - {
        "error": {
          "message": "You exceeded your current quota, please check your plan and billing details. For more information on this error, read the docs: https://platform.openai.com/docs/guides/error-codes/api-errors.",
          "type": "insufficient_quota",
          "param": null,
          "code": "insufficient_quota"
        }
      }


### OpenAI (https://platform.openai.com/)

Paid LLM that can be used to get accurate answers. ChatGPT is one of OpenAI's AI tool.

#### Scenarios implemented

1) system instruction provided to create a blog post.

       Controller method:
       public String blogPost(@RequestParam(value = "topic", defaultValue = "JDK Virtual Threads") String topic)
  
   The LLM goes through these instructions when generating the answer.


   ![img_2.png](img_2.png)

   Response given by OpenAI LLM for the above.


  ![img_3.png](img_3.png)


2) Read details from a image provided and return a text describing the image

       Controller method:
       @GetMapping("/read")
       public String describeImage()

![img_5.png](img_5.png)

    Response:
 
       The image shows a scenic landscape with a large pyramid-like structure in the center,
       surrounded by greenery and trees. There are several colorful hot air balloons floating in the sky above the pyramid.
       In the background, there are hills or mountains under a clear blue sky. The lighting suggests it might be early morning or late afternoon,
       casting a warm glow over the scene. The overall atmosphere is peaceful and picturesque.


3) Generate an image according to the ImageOptions provided

       Controller method:
       @GetMapping("/generate")
       public ResponseEntity<Map<String, String>> generateImage(
       @RequestParam(defaultValue = "a beautiful sunset over the ocean") String prompt)

![img_6.png](img_6.png)

   Response:

![img_4.png](img_4.png)

4) Generate audio file from a text

       Controller method:
       @GetMapping("/generate")
       public ResponseEntity<byte[]> speech(@RequestParam(defaultValue = "It's a great time to be a Java & Spring AI Engineer") String text)


![img_7.png](img_7.png)


   Response: 

   The audio file generated by OpenAI is attached in **resources/output_files/speech.mp3**
   

5) Chat Memory  - keep the conversation text in memory and respond using the meory values.

       Controller method:
       @RequestMapping("/chat")
       public String chatMemory(@RequestParam String message)

![img_8.png](img_8.png)


   Response:
   
![img_9.png](img_9.png)
   
![img_10.png](img_10.png)


6) RAG (Retrieval-Augmented Generation)

RAG is a technique that combines a knowledge base (specific to your business) with an AI language model (e.g. Open AI) to generate accurate, context-aware responses grounded in your own data rather than just the model's training data.

**How it Works**

    User asks a Question
          ↓
    [Embedding Model] → converts question to a vector
          ↓
    [Vector Database] → finds the most similar/relevant documents
          ↓
    [Retrieved Context + Original Question]
          ↓
    [LLM] → generates answer based on retrieved context
          ↓
    Accurate, Grounded Response

 **Example** 

    * Class files to refer
      RagConfiguration
      OpenAIVectorRAGController
      OpenAIVectorRAGService

* provide the context (refer models.json in resources). This is where the domain specific content is initially loaded.
  The models.json file contains the details of several LLM models by different companies (OpenAI, Google, Anthropic, Meta AI, etc.)

* When the application starts up, a SimpleVectorStore object is created with the models.json content and stored in-memory vector store.
  (refer vectorstore.json)

* User asks the question (in OpenAIVectorRAGController):

      Give me a list of all the models from OpenAI along with their context window.

* OpenAI model responses with the vector store context and the users question.
  only OpenAI related information is presented according to the users question.

  ![img_11.png](img_11.png)


