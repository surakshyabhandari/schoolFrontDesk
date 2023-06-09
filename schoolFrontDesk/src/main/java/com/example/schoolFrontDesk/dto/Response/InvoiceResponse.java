package com.example.schoolFrontDesk.dto.Response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class InvoiceResponse {
    private Long id;
    private LocalDate invoice_date;
    private LocalDate due_date;
    private Long days_passed;
    private Long invoice_year;
    private Long invoice_month;
    private String status;
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal total_amount;
}
