package com.example.schoolFrontDesk.controller;

import com.example.schoolFrontDesk.dto.AddRequest.AddStudentRequest;
import com.example.schoolFrontDesk.dto.AddRequest.LoginRequest;
import com.example.schoolFrontDesk.dto.Response.AuthenticationResponse;
import com.example.schoolFrontDesk.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

//    public StudentResponse addStudent(@ModelAttribute AddStudentRequest request, @RequestParam("file")MultipartFile file){

    @PostMapping(value = "/register")
    public ResponseEntity<AuthenticationResponse> register (
                @ModelAttribute AddStudentRequest request,
            @RequestParam("image")MultipartFile multipartFile){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody LoginRequest request){

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}