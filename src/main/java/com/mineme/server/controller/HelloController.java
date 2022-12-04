package com.mineme.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


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
        return ResponseEntity.ok().body("Hello"); 
    }
}
