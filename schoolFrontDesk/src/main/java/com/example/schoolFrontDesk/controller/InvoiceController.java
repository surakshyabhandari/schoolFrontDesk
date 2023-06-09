package com.example.schoolFrontDesk.controller;

import com.example.schoolFrontDesk.dto.AddRequest.AddInvoiceRequest;
import com.example.schoolFrontDesk.dto.Message;
import com.example.schoolFrontDesk.dto.Response.InvoiceResponse;
import com.example.schoolFrontDesk.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping(value = "/addInvoice")
    public InvoiceResponse addInvoice(@RequestBody AddInvoiceRequest request){
        log.info("add invoice::{}", request);
        return invoiceService.addInvoice(request);
    }
    @PutMapping(value = "/updateInvoice/{invoiceId}")
    public InvoiceResponse updateInvoice(@RequestBody AddInvoiceRequest request, @PathVariable Long invoiceId){
        log.info("update info :: {} [{}]", request, invoiceId);
        return invoiceService.updateInvoice(request,invoiceId);
    }
    @DeleteMapping(value = "/deleteInvoice/{invoiceId}")
    public Message deleteInvoice(@PathVariable Long invoiceId){
        log.info("delete invoice::[{}]", invoiceId);
        return invoiceService.deleteInvoice(invoiceId);
    }
    @GetMapping(value = "/getInvoice")
    public List<InvoiceResponse> getAllInvoice(){
        log.info("get all invoice");
        return invoiceService.getAllInvoice();
    }
}
