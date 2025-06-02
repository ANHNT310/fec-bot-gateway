package com.fec.bot.gateway.common.consts;

public class ApplicationConst {

    public static final String[] WHITELISTED_URLS = {
            "/authentication/token/**",
            "/actuator/**",
            "/swagger-ui/**",
            "/v3/**",
            "/notification/v3/api-docs",
            "/authentication/v3/api-docs",
            "/document/v3/api-docs",
            "/workflow/v3/api-docs",
            "/notification/ws/**"
    };
}
