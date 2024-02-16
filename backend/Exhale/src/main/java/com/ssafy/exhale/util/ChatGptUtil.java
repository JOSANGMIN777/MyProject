package com.ssafy.exhale.util;

import com.ssafy.exhale.dto.chatGpt.ChatGptRequest;
import com.ssafy.exhale.dto.chatGpt.ChatGptResponse;
import com.ssafy.exhale.dto.chatGpt.MessageRequest;
import com.ssafy.exhale.dto.responseDto.rehabilitationDto.FluencyCheckResponse;
import com.ssafy.exhale.exception.handler.ChatGptException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.*;

@Component
public class ChatGptUtil {
    @Value("${chatGpt.url}")
    private String chatGptUrl;

    @Value("${chatGpt.secretKey}")
    private String secretKey;

    @Value("${chatGpt.model}")
    private String model;

    private ChatGptRequest chatGptRequestDto;
    private HttpHeaders httpHeaders;

    private void setUserContent(String question, String answer) {
        String content = "Question: " + question + " Answer: " + answer;
        chatGptRequestDto.getMessages().get(1).setContent(content);
    }

    public FluencyCheckResponse postRequest(String question, String answer) {
        //요청 헤더 설정
        if(httpHeaders == null) {
            httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBearerAuth(secretKey);
        }
        //요청 바디 설정
        if(chatGptRequestDto == null) {
            List<MessageRequest> messagesDtoList = new ArrayList<>();
            String content = "For questions and answers, determine if the answer is grammatically, contextually, " +
                    "and semantically correct for the question, and if not, tell you why it is incorrect " +
                    "and the correct answer.";
            messagesDtoList.add(new MessageRequest("system", content));
            messagesDtoList.add(new MessageRequest("user", null));
            chatGptRequestDto = new ChatGptRequest(model, messagesDtoList);
        }

        RestTemplate restTemplate = new RestTemplate();
        setUserContent(question, answer);
        HttpEntity<ChatGptRequest> request = new HttpEntity<>(chatGptRequestDto, httpHeaders);

        FluencyCheckResponse fluencyCheckResponse = null;
        try {
            ChatGptResponse response = restTemplate.postForObject(
                    chatGptUrl,
                    request,
                    ChatGptResponse.class
            );

            String content = response.getChoices().get(0).getMessage().getContent();
            if (content.startsWith("맞았어요")) {
                fluencyCheckResponse = FluencyCheckResponse.of(true,  content);
            } else if (content.startsWith("틀렸어요")) {
                fluencyCheckResponse = FluencyCheckResponse.of(false, content);
            }
        } catch (RestClientException | NullPointerException restClientException) {
            throw new ChatGptException(restClientException);
        }
        return fluencyCheckResponse;
    }
}
