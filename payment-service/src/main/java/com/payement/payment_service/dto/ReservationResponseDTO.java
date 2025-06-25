package com.payement.payment_service.dto;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private Long reservationId;
    private String reservationCode;
    private GuestInfoDto guestInfo;
    private RoomInfoDto roomInfo;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;   // assuming status is a String in JSON response
    private int numOfGuests;
}
