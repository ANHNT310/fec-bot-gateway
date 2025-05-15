package com.fec.bpm.gatewayservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private String publicKey;
    private String serviceKey;

    public String getPublicString() {
        return publicKey.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");
    }
}
