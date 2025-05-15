package com.fec.bpm.gatewayservice.controller;

import com.fec.bpm.gatewayservice.common.dtos.BaseResponse;
import com.fec.bpm.gatewayservice.common.enums.ApplicationMessage;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class FallbackController {

    @RequestMapping("/fallback-document")
    public ResponseEntity<BaseResponse<?>> fallbackDocs() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(BaseResponse.failure(ApplicationMessage.SERVICE_UNAVAILABLE));
    }

    @RequestMapping("/fallback-authentication")
    public ResponseEntity<BaseResponse<?>> fallbackAuth() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(BaseResponse.failure(ApplicationMessage.SERVICE_UNAVAILABLE));
    }

    @RequestMapping("/fallback-bot-management")
    public ResponseEntity<BaseResponse<?>> fallbackBotManagement() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(BaseResponse.failure(ApplicationMessage.SERVICE_UNAVAILABLE));
    }
}
