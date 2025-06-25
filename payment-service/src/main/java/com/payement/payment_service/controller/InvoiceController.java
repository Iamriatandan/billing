package com.payement.payment_service.controller;
import com.payement.payment_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    // POST: generate and email invoice by orderId
    @PostMapping("/send/{orderId}")
    public ResponseEntity<String> sendInvoice(@PathVariable String orderId) {
        String message = invoiceService.generateAndSendInvoice(orderId);
        return ResponseEntity.ok(message);
    }
}