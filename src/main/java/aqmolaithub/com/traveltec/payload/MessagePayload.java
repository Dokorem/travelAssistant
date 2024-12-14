package aqmolaithub.com.traveltec.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagePayload {
    private String userId;
    private String message;
}
