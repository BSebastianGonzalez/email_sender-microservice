package com.example.email_sender.listener;

import com.example.email_sender.event.ClienteCreadoEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class EmailListener {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @RabbitListener(queues = "email.queue")
    public void onClienteCreado(ClienteCreadoEvent event) {
        System.out.println("📨 Enviando correo a " + event.getEmail());
        enviarCorreoBienvenida(event);
    }

    private void enviarCorreoBienvenida(ClienteCreadoEvent event) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            Context ctx = new Context();
            ctx.setVariable("nombre", event.getNombre());
            ctx.setVariable("email", event.getEmail());
            String html = templateEngine.process("bienvenida", ctx);

            helper.setTo(event.getEmail());
            helper.setSubject("Bienvenido, " + event.getNombre());
            helper.setText(html, true);


            mailSender.send(mimeMessage);
            System.out.println("✅ Correo enviado a " + event.getEmail());
        } catch (Exception e) {
            System.err.println("❌ Error enviando correo: " + e.getMessage());
        }
    }
}
