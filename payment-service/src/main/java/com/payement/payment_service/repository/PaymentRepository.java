package com.payement.payment_service.repository;
import com.payement.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
    Optional<Payment> findByReservationCode(String reservationCode);
}
