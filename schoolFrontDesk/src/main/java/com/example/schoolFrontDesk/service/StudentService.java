package com.example.schoolFrontDesk.service;

import com.example.schoolFrontDesk.dto.AddRequest.AddStudentRequest;
import com.example.schoolFrontDesk.dto.Message;
import com.example.schoolFrontDesk.dto.Response.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse addStudent(AddStudentRequest request);
    StudentResponse updateStudent(AddStudentRequest request, Long studentId);
    Message deleteStudent(Long studentId);
    List<StudentResponse> getStudents();

//    String uploadImage(MultipartFile file);
}
