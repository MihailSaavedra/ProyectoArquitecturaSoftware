package com.espe.proyecto.publicaciones_service.model;

import jakarta.persistence.*; // Importante añadir este
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class Publicacion {

    @Id
    private String id = UUID.randomUUID().toString();
    private String titulo;
    private String resumen;

    // CAMBIO: Ahora el estado es de tipo Enum
    @Enumerated(EnumType.STRING)
    private EstadoPublicacion estado = EstadoPublicacion.BORRADOR;

    private String autorPrincipalId;
}