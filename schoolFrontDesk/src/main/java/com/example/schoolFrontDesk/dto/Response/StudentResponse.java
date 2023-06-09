package com.example.schoolFrontDesk.dto.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class StudentResponse {

    private Long id;
    private String name;
    private LocalDate date_of_birth;
    private Long age;
    private LocalDate joined_date;
    private String image;
    private String password;
    private List<InvoiceResponse> invoiceId;
}