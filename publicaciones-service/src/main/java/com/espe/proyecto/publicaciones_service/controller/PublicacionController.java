package com.espe.proyecto.publicaciones_service.controller;

import com.espe.proyecto.publicaciones_service.model.EstadoPublicacion;
import com.espe.proyecto.publicaciones_service.model.Publicacion;
import com.espe.proyecto.publicaciones_service.repository.PublicacionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Inyectamos la herramienta para enviar mensajes a RabbitMQ
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Endpoint para crear un borrador (ya lo tenías)
    @PostMapping
    public ResponseEntity<Publicacion> crearBorrador(@RequestBody Publicacion publicacion) {
        publicacion.setEstado(EstadoPublicacion.BORRADOR);
        Publicacion publicacionGuardada = publicacionRepository.save(publicacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacionGuardada);
    }

    // Endpoint para obtener todas las publicaciones (ya lo tenías)
    @GetMapping
    public ResponseEntity<List<Publicacion>> obtenerTodas() {
        return ResponseEntity.ok(publicacionRepository.findAll());
    }

    // NUEVO: Endpoint para aprobar una publicación
    @PostMapping("/{id}/approve")
    public ResponseEntity<Publicacion> approvePublication(@PathVariable String id) {
        Optional<Publicacion> optionalPublicacion = publicacionRepository.findById(id);

        // Si no se encuentra la publicación, devuelve un error 404
        if (optionalPublicacion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Si se encuentra, la actualizamos
        Publicacion publicacion = optionalPublicacion.get();
        publicacion.setEstado(EstadoPublicacion.APROBADO);
        Publicacion publicacionAprobada = publicacionRepository.save(publicacion);

        // ==========================================================
        // ==      AQUÍ ENVIAMOS EL EVENTO A RABBITMQ              ==
        // ==========================================================
        String routingKey = "publication.approved"; // La "etiqueta" del mensaje
        String message = publicacionAprobada.getId(); // El contenido del mensaje (el ID)

        System.out.println("Enviando evento '" + routingKey + "' para ID: " + message);
        rabbitTemplate.convertAndSend("publication.events", routingKey, message);

        return ResponseEntity.ok(publicacionAprobada);
    }
}