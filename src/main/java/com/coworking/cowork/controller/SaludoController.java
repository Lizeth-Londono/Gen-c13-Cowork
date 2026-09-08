package com.coworking.cowork.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Este controlador contiene el primer endpoint creado para comprobar el funcionamiento de la API.
@RestController
public class SaludoController {

    // Al ingresar a /api/saludo se devuelve un mensaje sencillo de bienvenida.
    @GetMapping("/api/saludo")
    public String saludo() {
        return "Bienvenido a CoWork API, el espacio de LIZ en coworking";
    }
}