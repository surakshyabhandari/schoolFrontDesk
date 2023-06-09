package com.example.schoolFrontDesk.service.impl;

import com.example.schoolFrontDesk.config.JwtService;
import com.example.schoolFrontDesk.dto.AddRequest.AddStudentRequest;
import com.example.schoolFrontDesk.dto.Message;
import com.example.schoolFrontDesk.dto.Response.InvoiceResponse;
import com.example.schoolFrontDesk.dto.Response.StudentResponse;
import com.example.schoolFrontDesk.entity.Invoice;
import com.example.schoolFrontDesk.entity.Student;
import com.example.schoolFrontDesk.repository.InvoiceRepository;
import com.example.schoolFrontDesk.repository.StudentRepository;
import com.example.schoolFrontDesk.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final String FOLDER_PATH = "C:\\Users\\welcome\\Downloads\\images\\";
    @Autowired
    public StudentImpl(StudentRepository studentRepository, InvoiceRepository invoiceRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.studentRepository = studentRepository;
        this.invoiceRepository = invoiceRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public StudentResponse addStudent(AddStudentRequest request) {
        Student student = new Student();
        String filePath = FOLDER_PATH+request.getImage().getOriginalFilename();

        try {
            request.getImage().transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(request.getInvoiceId()!=null){
            List<Invoice> invoiceList = new ArrayList<>();
            for (Long invoice : request.getInvoiceId()){
                Invoice invoice1 = invoiceRepository.findById(invoice).orElse(null);
                invoiceList.add(invoice1);
            }
                student.setInvoiceId(invoiceList);
        }

        student.setName(request.getName());
        student.setDate_of_birth(request.getDate_of_birth());
        student.setAge(request.getAge());
        student.setJoined_date(request.getJoined_date());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setImage(filePath);
        Student save = studentRepository.save(student);
        return prepareStudentResponse(save);
    }

    @Override
    public StudentResponse updateStudent(AddStudentRequest request, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("Not a valid Id"));
        if(request.getName()!=null) student.setName(request.getName());
        if(request.getAge()!=null) student.setAge(request.getAge());
        if(request.getDate_of_birth()!=null) student.setDate_of_birth(request.getDate_of_birth());
        if(request.getJoined_date()!=null) student.setJoined_date(request.getJoined_date());
        if(request.getPassword()!=null) student.setPassword(request.getPassword());
        Student save = studentRepository.save(student);
        return prepareStudentResponse(save);
    }

    @Override
    public Message deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new RuntimeException("Not Valid Id"));
        studentRepository.delete(student);
        return Message.builder().message("Student Deleted Successfully").build();
    }

    @Override
    public List<StudentResponse> getStudents() {
        List<Student> studentList = studentRepository.findAll();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        studentList.forEach(student->studentResponseList.add(prepareStudentResponse(student)));
        return studentResponseList;
    }

    private StudentResponse prepareStudentResponse(Student save) {
        return StudentResponse.builder()
                .id(save.getId())
                .name(save.getName())
                .age(save.getAge())
                .date_of_birth(save.getDate_of_birth())
                .joined_date(save.getJoined_date())
                .password(passwordEncoder.encode(save.getPassword()))
                .invoiceId(prepareInvoiceResponse(save))
                .build();
    }




    private List<InvoiceResponse> prepareInvoiceResponse(Student save){
        return save.getInvoiceId() != null ? save.getInvoiceId().stream().map(this::prepareInvoiceResponse).collect(Collectors.toList()) : new ArrayList<>();

    }

    private InvoiceResponse prepareInvoiceResponse(Invoice invoice){
        return InvoiceResponse.builder()
                .id(invoice.getId())
                .invoice_date(invoice.getInvoice_date())
                .due_date(invoice.getDue_date())
                .days_passed(invoice.getDays_passed())
                .invoice_year(invoice.getInvoice_year())
                .invoice_month(invoice.getInvoice_month())
                .status(invoice.getStatus())
                .amount(invoice.getAmount())
                .discount(invoice.getDiscount())
                .total_amount(invoice.getTotal_amount())
                .build();
    }


}
