package com.payement.payment_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestInfoDto {
    private Long guestId;
    private String name;
    private String email;
    private String phoneNumber;
}
