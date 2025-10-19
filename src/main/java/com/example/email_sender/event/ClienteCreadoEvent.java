package com.example.email_sender.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCreadoEvent{
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
}
