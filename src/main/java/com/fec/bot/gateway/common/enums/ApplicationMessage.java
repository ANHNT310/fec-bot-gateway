package com.fec.bot.gateway.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ApplicationMessage {
    EMPTY("0", "0"),
    SUCCESS("000000", "Success."),
    BAD_REQUEST("000001", "Bad request."),
    SQL_HAD_ERROR("000002", "Sql exception."),
    UNAUTHORIZED("000005", "Unauthorized."),
    INTERNAL_SERVER_ERROR("999999", "Internal server error."),
    HAD_ERROR_THIRD_PARTY("000006", "Had error third party."),
    ACCESS_DENIED("000007", "Access Denied."),
    NOT_FOUND("000008", "Not found."),
    SERVICE_UNAVAILABLE("88888888", "Service Unavailable."),
    ;
    private final String code;
    private final String message;

    public static ApplicationMessage from(String code) {
        return Arrays.stream(ApplicationMessage.values())
                .filter(t -> t.name().equals(code))
                .findAny()
                .orElse(EMPTY);
    }
}
