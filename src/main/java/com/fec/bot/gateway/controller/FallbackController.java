package com.fec.bot.gateway.controller;

import com.fec.bot.gateway.common.dtos.BaseResponse;
import com.fec.bot.gateway.common.enums.ApplicationMessage;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class FallbackController {

    @GetMapping("/fallback/inventory")
    public ResponseEntity<BaseResponse<?>> fallbackInventory() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(BaseResponse.failure(ApplicationMessage.SERVICE_UNAVAILABLE));
    }
}
