package com.espe.proyecto.auth_service.controller;
import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String rol;
}
