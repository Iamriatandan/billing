package com.payement.payment_service.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;

    @Column(name = "reservation_code", nullable = false)
    private String reservationCode;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "receipt", nullable = false)
    private String receipt;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
