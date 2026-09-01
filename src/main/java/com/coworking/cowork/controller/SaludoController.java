package com.coworking.cowork.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SaludoController {

    @GetMapping("/api/saludo")
    public String saludo() {
        return "Bienvenido a CoWork API, el espacio de LIZ en coworking";
    }
}