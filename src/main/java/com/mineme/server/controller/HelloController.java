package com.mineme.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mineme.server.common.dto.ExceptionDto;
import com.mineme.server.common.dto.ResponseDto;
import com.mineme.server.common.enums.ErrorCode;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HelloController {

    /**
     * Request GET to /api/v1
     * @return "Hello." String
     */
    @GetMapping
    public ResponseEntity<?> retrieveHello(){
        
        return ResponseEntity.ok()
                            .body(new ResponseDto<>("Hello."));
    }

    /** 
     * This is Sample.
     * Request GET to /api/v1/fail
     * @return Bad Request.
     */
    @GetMapping("/fail")
    public ResponseEntity<?> retrieveFail(){
        
        return ResponseEntity.badRequest()
                            .body(ResponseDto.builder()
                                .success(false)
                                .error(ExceptionDto.builder()
                                    .code(ErrorCode.INVALID_DATA.getCode())
                                    .message(ErrorCode.INVALID_DATA.getMessage())
                                    .build())
                                .build());
    }
}
