package com.mineme.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.mineme.server.common.ResponseDto;
import com.mineme.server.common.ResponseSuccessDto;
import com.mineme.server.common.enums.ResponseCode;


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

        ResponseSuccessDto<String> response = new ResponseSuccessDto<String>
            (ResponseCode.STATUS_2001.getCode(), ResponseCode.STATUS_2001.getMessage(), "Hello.");
        
        return ResponseEntity.ok()
                            .body(response); 
    }

    /** 
     * This is Sample.
     * Request GET to /api/v1/fail
     * @return Bad Request.
     */
    @GetMapping("/fail")
    public ResponseEntity<?> retrieveFail(){

        ResponseDto response = ResponseDto.builder()
                                        .isSuccess(false)
                                        .code(ResponseCode.STATUS_4007.getCode())
                                        .message(ResponseCode.STATUS_4007.getMessage())
                                        .build();
        
        return ResponseEntity.badRequest()
                            .body(response); 
    }
}
