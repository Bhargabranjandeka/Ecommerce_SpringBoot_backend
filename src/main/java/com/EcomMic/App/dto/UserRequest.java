package com.EcomMic.App.dto;

import com.EcomMic.App.model.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressDto addressDto;

}
