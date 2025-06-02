package com.fec.bot.gateway.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fec.bot.gateway.common.dtos.BaseResponse;
import com.fec.bot.gateway.common.enums.ApplicationMessage;
import lombok.Getter;

import java.util.Objects;

/**
 * Custom exception class for handling application-specific errors.
 */
@Getter
public class ApplicationException extends RuntimeException {

    private final String code;
    private final String message;

    @JsonIgnore
    private final String detail;

    /**
     * Constructor with ApplicationMessage and an optional exception.
     *
     * @param applicationMessage the application message enum
     * @param exception          the root cause exception (optional)
     */
    public ApplicationException(ApplicationMessage applicationMessage, Exception exception) {
        this(applicationMessage, Objects.nonNull(exception) ? exception.getMessage() : null);
    }

    /**
     * Constructor with BaseResponse.
     *
     * @param response the base response containing error details
     * @param <T>      the type of the response body
     */
    public <T> ApplicationException(BaseResponse<T> response) {
        this.code = response.getCode();
        this.message = response.getMessage();
        this.detail = null;
    }

    /**
     * Constructor with ApplicationMessage.
     *
     * @param applicationMessage the application message enum
     */
    public ApplicationException(ApplicationMessage applicationMessage) {
        this(applicationMessage, (String) null);
    }

    /**
     * Constructor with ApplicationMessage and detail message.
     *
     * @param applicationMessage the application message enum
     * @param detail             the detailed error message
     */
    public ApplicationException(ApplicationMessage applicationMessage, String detail) {
        super(applicationMessage.getMessage());
        this.code = applicationMessage.getCode();
        this.message = applicationMessage.getMessage();
        this.detail = detail;
    }
}