package com.bankapp.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthServiceResponse {
    private boolean status;
    private String message;
    private Object data;
}
