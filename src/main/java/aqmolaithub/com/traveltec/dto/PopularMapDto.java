package aqmolaithub.com.traveltec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PopularMapDto {

    private int id;
    private String title;
    private String description;
    private String mapUrl;

}
