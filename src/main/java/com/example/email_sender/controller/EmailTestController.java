package com.example.email_sender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailTestController {
    private final JavaMailSender mailSender;

    @PostMapping("/test")
    public String sendTestEmail(@RequestParam String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Ya funciona");
            message.setText("ya funciona lo de enviar correos xddd, ahora me falta conectarlo con el de cliente");
            message.setFrom("bsgonzalez14a@gmail.com");

            mailSender.send(message);
            return "✅ Correo enviado correctamente a: " + to;
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error al enviar correo: " + e.getMessage();
        }
    }
}
