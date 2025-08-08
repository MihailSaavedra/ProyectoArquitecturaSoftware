package com.espe.proyecto.auth_service.repository;
import com.espe.proyecto.auth_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email); // Método para buscar por email
}