package com.payement.payment_service.feign;

import com.payement.payment_service.dto.ReservationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESERVATION-SERVICE")
public interface ReservationClient {

    @GetMapping("/reservations/code/{reservationCode}")
    ReservationResponseDTO getReservationByCode(@PathVariable String reservationCode);
}