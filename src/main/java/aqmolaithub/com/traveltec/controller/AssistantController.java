package aqmolaithub.com.traveltec.controller;

import aqmolaithub.com.traveltec.dto.AssistantMessageResponseDto;
import aqmolaithub.com.traveltec.payload.MessagePayload;
import aqmolaithub.com.traveltec.service.AiAssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travel-assistant-api/v1")
public class AssistantController {

    private final AiAssistantService aiAssistantService;

    @PostMapping("/getAssistantMessage")
    public ResponseEntity<AssistantMessageResponseDto> getAssistantMessage(@RequestBody MessagePayload messagePayload) {
        String messageContent = messagePayload.getMessage();
        String userId = messagePayload.getUserId();
        System.out.println("Message content: " + messageContent);
        System.out.println("User ID: " + userId);
        if(messageContent == null || messageContent.isBlank() || userId == null || userId.isBlank()) {
            System.out.println("Произошла какая то залупа");
            return ResponseEntity.badRequest()
                    .build();
        }
        System.out.println("Залупа не произошла");
        String assistantAnswer = this.aiAssistantService.sendMessage(userId , messageContent);
        AssistantMessageResponseDto responseDto = new AssistantMessageResponseDto(assistantAnswer);
        return ResponseEntity.ok()
                .body(responseDto);
    }

}
