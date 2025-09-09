package com.EcomMic.App.dto;

import com.EcomMic.App.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDto addressDto;
}
