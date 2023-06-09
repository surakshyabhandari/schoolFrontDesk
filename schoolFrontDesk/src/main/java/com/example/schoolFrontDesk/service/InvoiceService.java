package com.example.schoolFrontDesk.service;

import com.example.schoolFrontDesk.dto.AddRequest.AddInvoiceRequest;
import com.example.schoolFrontDesk.dto.Message;
import com.example.schoolFrontDesk.dto.Response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {

    InvoiceResponse addInvoice(AddInvoiceRequest request);
    InvoiceResponse updateInvoice(AddInvoiceRequest request, Long invoiceId);
    Message deleteInvoice(Long invoiceId);
    List<InvoiceResponse> getAllInvoice();
}
