package com.PCThanhCong.models;

import com.PCThanhCong.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private String token;
    private User user;
}