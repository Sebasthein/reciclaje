package com.example.reciclaje.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reciclaje.entidades.Usuario;
import com.example.reciclaje.servicio.UsuarioServicio;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioServicio usuarioServicio;

    public UsuarioController(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Registrar un nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioRegistrado = usuarioServicio.registrarUsuario(usuario);
            return ResponseEntity.ok(usuarioRegistrado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Iniciar sesión y obtener token JWT
    @PostMapping("/login")
    public ResponseEntity<String> iniciarSesion(
            @RequestParam String email,
            @RequestParam String password) {
        try {
            String token = usuarioServicio.iniciarSesionYGenerarToken(email, password);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Obtener perfil por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPerfil(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioServicio.obtenerPerfil(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener perfil por email
    @GetMapping("/por-email")
    public ResponseEntity<Usuario> obtenerPerfilPorEmail(@RequestParam String email) {
        try {
            Usuario usuario = usuarioServicio.obtenerPerfilPorEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Agregar puntos a un usuario
    @PostMapping("/{usuarioId}/puntos")
    public ResponseEntity<Usuario> agregarPuntos(
            @PathVariable Long usuarioId,
            @RequestParam int puntos) {
        try {
            Usuario usuario = usuarioServicio.agregarPuntos(usuarioId, puntos);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}