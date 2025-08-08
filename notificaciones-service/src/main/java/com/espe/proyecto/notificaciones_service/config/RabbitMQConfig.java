package com.espe.proyecto.notificaciones_service.config; // Revisa que el paquete sea correcto

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "publication.events";
    public static final String QUEUE_NAME = "notifications.activity";
    public static final String ROUTING_KEY_APPROVED = "publication.approved";

    // 1. Crea el "apartado" general para eventos de publicaciones
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // 2. Crea el "buzón" específico para las notificaciones
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    // 3. Crea la "unión": le dice a RabbitMQ que todos los mensajes con la etiqueta
    // "publication.approved" que lleguen al exchange, deben ser enviados a nuestro buzón.
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_APPROVED);
    }
}