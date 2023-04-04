package com.hckj.chatgpt.service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Service
public class ChatGPTService {
	 private static final Logger LOGGER = LoggerFactory.getLogger(ChatGPTService.class);
	    @Value("${gpt.token}")
	    private String token;	
	    
	    @Value("${gpt.apikey}")
	    private String apiKey;	
	    
	    @Value("${gpt.llmodel}")
	    private String llModel;

	    @Value("${proxies.ip}")
	    private String ip;
	    
	    @Value("${proxies.port}")
	    private int port;
	    
	    @Value("${proxies.useproxy}")
	    private Boolean useProxy;
	    
	    private static StringBuilder tempMsg;
	    private static final Duration timeout = Duration.ofSeconds(20);
	    
	    public String message(String chatContent) {
	    	LOGGER.info("ChatGPTService message："+chatContent);
	    	ObjectMapper mapper = OpenAiService.defaultObjectMapper();	    	
	    	OkHttpClient client = null;
	    	if(StringUtils.isEmpty(apiKey)) {
	    		apiKey = token;
	    	}
	    	if(useProxy) {
	    		LOGGER.info("The agent is not in use");
	    		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
	    		client = OpenAiService.defaultClient(apiKey, timeout)
		    	        .newBuilder()
		    	        .proxy(proxy)
		    	        .build();
	    	}else {
	    		LOGGER.info("The agent is not in use");
	    		client = OpenAiService.defaultClient(apiKey, timeout)
		    	        .newBuilder()
		    	        .build();
	    	}
	    	LOGGER.info("ChatGPTService useProxy："+useProxy);
	    	Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper);
	    	OpenAiApi api = retrofit.create(OpenAiApi.class);
	    	OpenAiService service = new OpenAiService(api);
	    	LOGGER.info("ChatGPTService llModel："+llModel);
	    	 final List<ChatMessage> messages = new ArrayList<>();
	         final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), chatContent);
	         messages.add(systemMessage);
	         tempMsg= new StringBuilder();
	         ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
	                 .builder()
	                 .model(llModel)
	                 .messages(messages)
	                 .n(1)
//	                 .maxTokens(50) default 4096
	                 .logitBias(new HashMap<>())
	                 .build();
	         service.streamChatCompletion(chatCompletionRequest)
	                 .doOnError(Throwable::printStackTrace)
	                 .blockingForEach(ChatGPTService::set);	        
//	         service.shutdownExecutor();
	    	return tempMsg.toString().replaceAll("null", "");
	    }
	    public static void set(ChatCompletionChunk chat) {
	    	LOGGER.info("ChatGPTService set："+tempMsg.toString());
	    	for(ChatCompletionChoice tem:chat.getChoices()) {
	    		LOGGER.info("ChatGPTService set Choice："+tem.getMessage().getContent());
	    		tempMsg.append(tem.getMessage().getContent()).append(" ");
	    	}
	    }
}
