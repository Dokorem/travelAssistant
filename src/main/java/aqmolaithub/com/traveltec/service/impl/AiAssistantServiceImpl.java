package aqmolaithub.com.traveltec.service.impl;

import aqmolaithub.com.traveltec.client.OpenAiClient;
import aqmolaithub.com.traveltec.service.AiAssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiAssistantServiceImpl implements AiAssistantService {

    private final OpenAiClient openAiClient;

    @Override
    public String sendMessage(String userId, String userMessage) {
        String threadId = this.openAiClient.createThread();
        this.openAiClient.createMessage(threadId, userMessage);
        String runId = this.openAiClient.createRun(threadId);
        return this.openAiClient.retrieveRun(threadId, runId)
                .replaceFirst("【4:0†travelTechAssistantSettings.txt】", "");
    }
}
