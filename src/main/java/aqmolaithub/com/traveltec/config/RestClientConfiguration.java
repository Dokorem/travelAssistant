package aqmolaithub.com.traveltec.config;

import aqmolaithub.com.traveltec.client.impl.OpenAiClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    public OpenAiClientImpl openAiRestClient(@Value("${openai.uri:https://api.openai.com/v1}") String uri) {
        return new OpenAiClientImpl(RestClient.builder()
                .baseUrl(uri)
                .build());
    }

}
