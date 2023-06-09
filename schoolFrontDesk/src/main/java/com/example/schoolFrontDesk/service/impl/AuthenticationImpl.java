package com.example.schoolFrontDesk.service.impl;

import com.example.schoolFrontDesk.config.JwtService;
import com.example.schoolFrontDesk.dto.AddRequest.AddStudentRequest;
import com.example.schoolFrontDesk.dto.AddRequest.LoginRequest;
import com.example.schoolFrontDesk.dto.Response.AuthenticationResponse;
import com.example.schoolFrontDesk.entity.Student;
import com.example.schoolFrontDesk.repository.StudentRepository;
import com.example.schoolFrontDesk.service.AuthenticationService;
import com.example.schoolFrontDesk.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationImpl implements AuthenticationService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StudentService studentService;

    @Override
    public AuthenticationResponse register(AddStudentRequest request) {
        var student = Student.builder()
                .name(request.getName())
                .age(request.getAge())
                .date_of_birth(request.getDate_of_birth())
                .joined_date(request.getJoined_date())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var jwtToken = jwtService.generateToken(student);
        studentService.addStudent(request);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        passwordEncoder.encode(request.getPassword())
                )
        );
        var student = studentRepository.findByName(request.getName());
        var jwtToken = jwtService.generateToken((UserDetails) student);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
