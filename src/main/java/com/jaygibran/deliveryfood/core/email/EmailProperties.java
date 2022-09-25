package com.jaygibran.deliveryfood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("deliveryfood.email")
public class EmailProperties {

    private String sender;
    private Implementation impl = Implementation.FAKE;
    
    public enum Implementation{
        SMTP, FAKE;
    }
}
