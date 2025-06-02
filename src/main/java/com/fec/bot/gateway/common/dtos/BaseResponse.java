package com.fec.bot.gateway.common.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fec.bot.gateway.common.enums.ApplicationMessage;
import com.fec.bot.gateway.exception.ApplicationException;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public final class BaseResponse<TData> {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private TData data;

    @JsonProperty("detail")
    private String detailError;

    /**
     * Checks if the response indicates an error.
     *
     * @return true if the response has an error, false otherwise.
     */
    public boolean hasError() {
        return !ApplicationMessage.SUCCESS.getCode().equalsIgnoreCase(this.code);
    }

    /**
     * Creates a success response with data.
     *
     * @param data the response data.
     * @return a success BaseResponse instance.
     */
    public static <TData> BaseResponse<TData> success(TData data) {
        return BaseResponse.<TData>builder()
                .code(ApplicationMessage.SUCCESS.getCode())
                .message(ApplicationMessage.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    /**
     * Creates a failure response with an ApplicationMessage.
     *
     * @param message the application message.
     * @return a failure BaseResponse instance.
     */
    public static <TData> BaseResponse<TData> failure(ApplicationMessage message) {
        return BaseResponse.<TData>builder()
                .code(message.getCode())
                .message(message.getMessage())
                .build();
    }

    /**
     * Creates a failure response with an ApplicationException.
     *
     * @param exception the application exception.
     * @return a failure BaseResponse instance.
     */
    public static <TData> BaseResponse<TData> failure(ApplicationException exception) {
        return BaseResponse.<TData>builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .detailError(exception.getDetail())
                .build();
    }
}