package com.fec.bot.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "gateway")
public class RouteUriProperties {

    private Map<String, String> routes = new HashMap<>();

    public String getUriById(String routeId) {
        return routes.get(routeId);
    }
}
