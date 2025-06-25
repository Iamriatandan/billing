package com.payement.payment_service.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private String orderId;
    private String status;
    private String receipt;
    private double amount;
}
