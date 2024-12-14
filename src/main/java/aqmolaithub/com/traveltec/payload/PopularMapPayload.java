package aqmolaithub.com.traveltec.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PopularMapPayload {

    private String title;
    private String description;
    private String mapUrl;

}
