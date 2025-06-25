package com.payement.payment_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfoDto {
    private Long roomId;
    private String roomNumber;
    private String roomType;
    private int pricePerNight;
    private String status;
}