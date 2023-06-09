package com.example.schoolFrontDesk.service;

import com.example.schoolFrontDesk.dto.AddRequest.AddStudentRequest;
import com.example.schoolFrontDesk.dto.AddRequest.LoginRequest;
import com.example.schoolFrontDesk.dto.Response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse register (AddStudentRequest request);
    AuthenticationResponse authenticate(LoginRequest request);

}
