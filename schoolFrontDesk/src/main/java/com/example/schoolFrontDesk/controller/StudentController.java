package com.example.schoolFrontDesk.controller;

import com.example.schoolFrontDesk.dto.AddRequest.AddStudentRequest;
import com.example.schoolFrontDesk.dto.Message;
import com.example.schoolFrontDesk.dto.Response.StudentResponse;
import com.example.schoolFrontDesk.service.AuthenticationService;
import com.example.schoolFrontDesk.service.StudentService;
import com.example.schoolFrontDesk.util.PdfExporter;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class StudentController {

    private final StudentService studentService;
    private final AuthenticationService authenticationService;

    @Autowired
    public StudentController(StudentService studentService, AuthenticationService authenticationService) {
        this.studentService = studentService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/addStudent")
    public StudentResponse addStudent(@ModelAttribute AddStudentRequest request, @RequestParam("file")MultipartFile file){
        request.setImage(file);
        log.info("add student :: {} [{}]", request);
        return studentService.addStudent(request);
    }

    @PutMapping(value = "/updateStudent/{studentId}")
    public StudentResponse updateStudent(@RequestBody AddStudentRequest request, @PathVariable Long studentId){
        log.info("update student::{}[{}]",request,studentId);
        return studentService.updateStudent(request,studentId);
    }

    @DeleteMapping(value = "/deleteStudent/{studentId}")
    public Message deleteStudent(@PathVariable Long studentId){
        log.info("delete post::[{}]",studentId);
        return studentService.deleteStudent(studentId);
    }

    @GetMapping(value = "/getAllStudent")
    public List<StudentResponse> getStudents(){
        log.info("get all students");
        return studentService.getStudents();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws DocumentException, IOException{
        response.setContentType("Student/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<StudentResponse> students = studentService.getStudents();

        PdfExporter exporter = new PdfExporter(students);
        exporter.export(response);
    }
}