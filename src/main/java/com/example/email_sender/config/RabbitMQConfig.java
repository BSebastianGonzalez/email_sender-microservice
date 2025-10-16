package com.example.email_sender.config;

import com.example.email_sender.event.ClienteCreadoEvent;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    @Bean
    public MessageConverter jackson2MessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        // Mapea el tipo enviado desde el otro servicio al tipo local
        idClassMapping.put("com.example.client_register.event.ClienteCreadoEvent", ClienteCreadoEvent.class);
        classMapper.setIdClassMapping(idClassMapping);

        // Permitir paquetes de confianza (ajustar según seguridad)
        classMapper.setTrustedPackages("com.example.client_register.event", "com.example.email_sender.event");

        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email.queue", true);
    }
}

