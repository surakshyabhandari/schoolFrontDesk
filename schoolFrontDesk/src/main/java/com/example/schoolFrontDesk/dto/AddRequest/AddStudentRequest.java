package com.example.schoolFrontDesk.dto.AddRequest;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class AddStudentRequest {

    private String name;
    private LocalDate date_of_birth;
    private Long age;
    private LocalDate joined_date;
    private MultipartFile image;
    private String password;
    private List<Long> invoiceId;

}
