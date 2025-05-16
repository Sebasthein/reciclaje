package com.example.reciclaje.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reciclaje.entidades.Reciclaje;
import com.example.reciclaje.seguridad.CustomUserDetails;
import com.example.reciclaje.servicio.ReciclajeServicio;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reciclajes")
@RequiredArgsConstructor
public class ReciclajeController {

	private final ReciclajeServicio reciclajeService;

	@PostMapping("/registrar")
	public ResponseEntity<Reciclaje> registrarReciclaje(@RequestBody Map<String, String> datos, Authentication authentication) {
	    // 1. Obtener email del usuario autenticado
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    String email = userDetails.getUsername();

	    // 2. Extraer materialId y foto del body
	    Long materialId = Long.parseLong(datos.get("materialId"));
	    String fotoUrl = datos.get("fotoUrl");

	    // 3. Registrar reciclaje usando solo el email
	    Reciclaje reciclaje = reciclajeService.registrarReciclaje(email, materialId, fotoUrl);

	    return ResponseEntity.status(HttpStatus.CREATED).body(reciclaje);
	}

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reciclaje>> reciclajesPorUsuario(@PathVariable Long usuarioId) {
        List<Reciclaje> lista = reciclajeService.obtenerReciclajesPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/validar/{id}")
    public ResponseEntity<Reciclaje> validar(@PathVariable Long id) {
        Reciclaje reciclajeValidado = reciclajeService.validarReciclaje(id);
        return ResponseEntity.ok(reciclajeValidado);
    }
    
  
}
