package com.mineme.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.mineme.server.common.ResponseDto;
import com.mineme.server.common.env.ResponseCode;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HelloController {

    /**
     * 
     * @param request
     * @param response
     * @return
     */
    @GetMapping
    public ResponseEntity<?> retrieveHello(){

        ResponseDto<String> response = ResponseDto.<String>builder()
                                        .isSuccess(true)
                                        .code(ResponseCode.STATUS_2001.getCode())
                                        .message(ResponseCode.STATUS_2001.getMessage())
                                        .data("Hello.")
                                        .build();
        
        return ResponseEntity.ok()
                            .body(response); 
    }
}
