package com.coworking.cowork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Esta es la clase principal desde la cual se inicia la aplicación CoWork.
@SpringBootApplication
public class CoworkApplication {

	// Aquí Spring Boot prepara el contexto, conecta las capas e inicia el servidor.
	public static void main(String[] args) {
		SpringApplication.run(CoworkApplication.class, args);
	}
}