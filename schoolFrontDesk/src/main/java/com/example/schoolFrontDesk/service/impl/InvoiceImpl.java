package com.example.schoolFrontDesk.service.impl;

import com.example.schoolFrontDesk.dto.AddRequest.AddInvoiceRequest;
import com.example.schoolFrontDesk.dto.Message;
import com.example.schoolFrontDesk.dto.Response.InvoiceResponse;
import com.example.schoolFrontDesk.entity.Invoice;
import com.example.schoolFrontDesk.entity.Status;
import com.example.schoolFrontDesk.repository.InvoiceRepository;
import com.example.schoolFrontDesk.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public InvoiceResponse addInvoice(AddInvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setInvoice_date(request.getInvoice_date());
        invoice.setDue_date(request.getDue_date());
        invoice.setDays_passed(request.getDays_passed());
        invoice.setInvoice_year(request.getInvoice_year());
        invoice.setInvoice_month(request.getInvoice_month());
        invoice.setStatus(validateStatus(request.getStatus()));
        invoice.setAmount(request.getAmount());
        invoice.setDiscount(request.getDiscount());
        invoice.setTotal_amount(request.getTotal_amount());
        Invoice save = invoiceRepository.save(invoice);
        return prepareInvoiceResponse(save);
    }

    @Override
    public InvoiceResponse updateInvoice(AddInvoiceRequest request, Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(()->new RuntimeException("Not Valid Id"));
        if(request.getInvoice_date()!=null) invoice.setInvoice_date(request.getInvoice_date());
        if(request.getDue_date()!=null) invoice.setDue_date(request.getDue_date());
        if(request.getDays_passed()!=null) invoice.setDays_passed(request.getDays_passed());
        if(request.getInvoice_year()!=null) invoice.setInvoice_year(request.getInvoice_year());
        if(request.getInvoice_month()!=null) invoice.setInvoice_month(request.getInvoice_month());
        if(request.getStatus()!=null) invoice.setStatus(validateStatus(request.getStatus()));
        if(request.getAmount()!=null) invoice.setAmount(request.getAmount());
        if(request.getDiscount()!=null) invoice.setDiscount(request.getDiscount());
        if(request.getTotal_amount()!=null) invoice.setTotal_amount(request.getTotal_amount());
        Invoice save = invoiceRepository.save(invoice);
        return prepareInvoiceResponse(save);
    }

    @Override
    public Message deleteInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(()->new RuntimeException("Not Valid Id"));
        invoiceRepository.delete(invoice);
        return Message.builder().message("Invoice Deleted Successfully").build();
    }

    @Override
    public List<InvoiceResponse> getAllInvoice() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
        invoiceList.forEach(invoice->invoiceResponseList.add(prepareInvoiceResponse(invoice)));
        return invoiceResponseList;
    }

    private InvoiceResponse prepareInvoiceResponse(Invoice save) {
        return InvoiceResponse.builder()
                .id(save.getId())
                .invoice_date(save.getInvoice_date())
                .due_date(save.getDue_date())
                .days_passed(save.getDays_passed())
                .invoice_year(save.getInvoice_year())
                .invoice_month(save.getInvoice_month())
                .status(save.getStatus())
                .amount(save.getAmount())
                .discount(save.getDiscount())
                .total_amount(save.getTotal_amount())
                .build();
    }
    private String validateStatus(String status){
        for(Status status1: Status.values()){
            if (status.equals(status1.name())) return status1.name();
        }
        throw new RuntimeException("Please choose valid status");
    }
}
