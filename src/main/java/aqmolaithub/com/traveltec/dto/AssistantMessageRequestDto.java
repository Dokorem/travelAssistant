package aqmolaithub.com.traveltec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssistantMessageRequestDto {

    private String role;
    private String content;

}
