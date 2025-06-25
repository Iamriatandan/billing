package com.payement.payment_service.controller;
import com.payement.payment_service.dto.PaymentRequestDTO;
import com.payement.payment_service.dto.PaymentResponseDTO;
import com.payement.payment_service.service.PaymentService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // POST: create payment order based on reservation code
    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO requestDTO) {
        try {
            PaymentResponseDTO responseDTO = paymentService.createOrder(requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}