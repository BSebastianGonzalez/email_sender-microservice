package com.example.email_sender.listener;

import com.example.email_sender.event.ClienteCreadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailListener {

    private final JavaMailSender mailSender;

    @RabbitListener(queues = "email.queue")
    public void onClienteCreado(ClienteCreadoEvent event) {
        System.out.println("📨 Enviando correo a " + event.getEmail());
        enviarCorreoBienvenida(event);
    }

    private void enviarCorreoBienvenida(ClienteCreadoEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject("Bienvenido, " + event.getNombre());
        message.setText("Hola " + event.getNombre() + ", gracias por registrarte con nosotros!");
        mailSender.send(message);
    }
}
