package aqmolaithub.com.traveltec.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "telegram")
@Data
public class BotProperties {

    private String botName;
    private String botToken;

}
