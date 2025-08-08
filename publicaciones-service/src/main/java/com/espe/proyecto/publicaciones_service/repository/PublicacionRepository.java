package com.espe.proyecto.publicaciones_service.repository;

import com.espe.proyecto.publicaciones_service.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Anotación de Spring
public interface PublicacionRepository extends JpaRepository<Publicacion, String> {
    // La magia sucede aquí
}