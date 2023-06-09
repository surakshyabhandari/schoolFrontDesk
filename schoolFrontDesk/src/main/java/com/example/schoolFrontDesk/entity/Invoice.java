package com.example.schoolFrontDesk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    private Student student;
}
