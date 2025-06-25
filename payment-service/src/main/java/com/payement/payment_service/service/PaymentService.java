package com.payement.payment_service.service;
import com.payement.payment_service.dto.PaymentRequestDTO;
import com.payement.payment_service.dto.PaymentResponseDTO;
import com.payement.payment_service.dto.ReservationResponseDTO;
import com.payement.payment_service.entity.Payment;
import com.payement.payment_service.feign.ReservationClient;
import com.payement.payment_service.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RazorpayClient razorpayClient;
    private final ReservationClient reservationClient;
    private final PaymentRepository paymentRepository;

    public PaymentResponseDTO createOrder(PaymentRequestDTO requestDTO) throws RazorpayException {
        // Fetch reservation details via Feign
        ReservationResponseDTO reservation = reservationClient.getReservationByCode(requestDTO.getReservationCode());

        // Extract relevant data
        LocalDate checkIn = reservation.getCheckInDate();
        LocalDate checkOut = reservation.getCheckOutDate();
        int numGuests = reservation.getNumOfGuests();
        int pricePerNight = reservation.getRoomInfo().getPricePerNight();
        String roomType = reservation.getRoomInfo().getRoomType();
        String currency = "INR";

        // Calculate stay duration
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights <= 0) nights = 1; // minimum 1 night billing

        // Compute total amount
        double totalAmount = nights * pricePerNight;

        // Optional: add roomType-based pricing logic if needed

        // Create receipt number
        String receipt = "rcpt_" + UUID.randomUUID();

        // Create Razorpay order
        JSONObject options = new JSONObject();
        options.put("amount", (int) (totalAmount * 100)); // in paise
        options.put("currency", currency);
        options.put("receipt", receipt);
        options.put("payment_capture", 1);

        Order order = razorpayClient.orders.create(options);

        // Save payment details in DB
        Payment payment = new Payment();
        payment.setOrderId(order.get("id"));
        payment.setReservationCode(reservation.getReservationCode());
        payment.setAmount(totalAmount);
        payment.setCurrency(currency);
        payment.setReceipt(receipt);
        payment.setStatus(order.get("status"));
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        // Prepare response DTO
        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        responseDTO.setOrderId(order.get("id"));
        responseDTO.setStatus(order.get("status"));
        responseDTO.setReceipt(receipt);
        responseDTO.setAmount(totalAmount);

        return responseDTO;
    }
}