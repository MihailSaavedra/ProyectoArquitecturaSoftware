package com.espe.proyecto.auth_service.model; 
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String email;
    private String password;
    private String rol; // "ROLE_AUTOR", "ROLE_EDITOR"
}