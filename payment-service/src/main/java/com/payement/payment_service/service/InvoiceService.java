package com.payement.payment_service.service;
import com.payement.payment_service.dto.ReservationResponseDTO;
import com.payement.payment_service.entity.Payment;
import com.payement.payment_service.feign.ReservationClient;
import com.payement.payment_service.repository.PaymentRepository;
import com.payement.payment_service.util.PDFInvoiceGenerator;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final ReservationClient reservationClient;
    private final PaymentRepository paymentRepository;
    private final EmailService emailService;

    public String generateAndSendInvoice(String orderId) {
        try {
            // Fetch payment details by orderId
            Optional<Payment> paymentOpt = paymentRepository.findByOrderId(orderId);
            if (paymentOpt.isEmpty()) {
                return "Payment not found for orderId: " + orderId;
            }

            Payment payment = paymentOpt.get();

            // Fetch reservation details via Feign
            ReservationResponseDTO reservation = reservationClient.getReservationByCode(payment.getReservationCode());

            // Generate PDF invoice as byte array
            byte[] pdfBytes = PDFInvoiceGenerator.generateInvoice(payment, reservation);

            // Send email with PDF attachment
            emailService.sendInvoiceWithAttachment(
                    reservation.getGuestInfo().getEmail(),
                    "Invoice for your Reservation - " + reservation.getReservationCode(),
                    "Dear " + reservation.getGuestInfo().getName() + ",\n\nPlease find attached your invoice for reservation "
                            + reservation.getReservationCode() + ".\n\nThank you for choosing Classy Stay Hotels!",
                    pdfBytes,
                    "Invoice_" + reservation.getReservationCode() + ".pdf"
            );

            return "Invoice PDF emailed successfully to " + reservation.getGuestInfo().getEmail();

        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send invoice email for orderId: " + orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating invoice for orderId: " + orderId;
        }
    }
}