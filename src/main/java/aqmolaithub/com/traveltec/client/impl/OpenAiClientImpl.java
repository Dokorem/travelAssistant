package aqmolaithub.com.traveltec.client.impl;

import aqmolaithub.com.traveltec.client.OpenAiClient;
import aqmolaithub.com.traveltec.dto.AssistantMessageRequestDto;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiClientImpl implements OpenAiClient {

    @Value(value = "${openai.assistantKey}")
    private String assistantId;

    @Value(value = "${openai.apiKey}")
    private String apiKey;

    private final RestClient restClient;

    @Override
    public String createThread() {
        return this.restClient.post()
                .uri("/threads")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header("OpenAI-Beta", "assistants=v2")
                .contentType(MediaType.APPLICATION_JSON)
                .body("")
                .retrieve()
                .body(Map.class)
                .get("id")
                .toString();
    }

    @Override
    public void createMessage(String threadId, String messageContent) {
        AssistantMessageRequestDto assistantMessageResponseDto = AssistantMessageRequestDto.builder()
                .role("user")
                .content(messageContent)
                .build();
        this.restClient.post()
                .uri("/threads/%s/messages".formatted(threadId))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header("OpenAI-Beta", "assistants=v2")
                .contentType(MediaType.APPLICATION_JSON)
                .body(assistantMessageResponseDto)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public String createRun(String threadId) {
        return this.restClient.post()
                .uri("/threads/%s/runs".formatted(threadId))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header("OpenAI-Beta", "assistants=v2")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("assistant_id", this.assistantId))
                .retrieve()
                .body(Map.class)
                .get("id")
                .toString();
    }

    @Override
    public String retrieveRun(String threadId, String runId) {
        String openAiUri = "/threads/%s/runs/%s".formatted(threadId, runId);
        Map<String, Object> response = runInfoResponse(openAiUri);

        String status = response.get("status").toString();
        while(!"completed".equals(status)) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            response = runInfoResponse(openAiUri);
            status = response.get("status").toString();
        }
        List<Map<String, Object>> messages = (List<Map<String, Object>>) this.restClient.get()
                .uri("https://api.openai.com/v1/threads/{threadId}/messages", threadId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header("OpenAI-Beta", "assistants=v2")
                .retrieve()
                .body(Map.class)
                .get("data");
        List<Map<String, Map<String, Object>>> content = (List<Map<String, Map<String, Object>>>) messages.get(0)
                .get("content");

        return (String) content.get(0)
                .get("text")
                .get("value");
    }

    private Map<String, Object> runInfoResponse(String openAiUri) {
        return this.restClient.get()
                .uri(openAiUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header("OpenAI-Beta", "assistants=v2")
                .retrieve()
                .body(Map.class);
    }

}
