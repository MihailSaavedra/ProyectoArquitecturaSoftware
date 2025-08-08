package com.espe.proyecto.notificaciones_service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    // Con esta anotación, Spring automáticamente hará que este método
    // se ejecute cada vez que un mensaje llegue a la cola "notifications.activity".
    @RabbitListener(queues = "notifications.activity")
    public void onPublicationApproved(String publicationId) {
        // En un proyecto real, aquí iría el código para enviar un email.
        // Para nuestra demo, simplemente lo imprimimos en la consola.
        System.out.println("");
        System.out.println("======================================================");
        System.out.println("### NOTIFICACIÓN RECIBIDA ###");
        System.out.println("La publicación con ID: " + publicationId + " ha sido aprobada.");
        System.out.println("Acción: Enviando email de notificación al autor...");
        System.out.println("======================================================");
        System.out.println("");
    }
}