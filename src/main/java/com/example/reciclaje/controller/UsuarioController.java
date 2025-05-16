package com.example.reciclaje.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reciclaje.entidades.Usuario;
import com.example.reciclaje.seguridad.CustomUserDetails;
import com.example.reciclaje.servicio.UsuarioServicio;
import com.example.reciclaje.servicioDTO.UsuarioDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	 private final UsuarioServicio usuarioService;

	    @PostMapping("/auth/registro")
	    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
	        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
	        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
	    }

	    @PostMapping("/auth/login")
	    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credenciales) {
	        String email = credenciales.get("email");
	        String password = credenciales.get("password");

	        String token = usuarioService.iniciarSesionYGenerarToken(email, password);

	        Map<String, String> respuesta = new HashMap<>();
	        respuesta.put("token", token);

	        return ResponseEntity.ok(respuesta);
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<Usuario> obtenerPerfil(@PathVariable Long id) {
	        Usuario usuario = usuarioService.obtenerPerfil(id);
	        return ResponseEntity.ok(usuario);
	    }
	    
	    @GetMapping("/perfil")
	    public ResponseEntity<Usuario> obtenerPerfilDesdeToken(Authentication authentication) {
	        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	        String email = userDetails.getUsername();
	        Usuario usuario = usuarioService.obtenerPerfilPorEmail(email);
	        return ResponseEntity.ok(usuario);
	    }
	    
	    // Agregar puntos al usuario y actualizar nivel
	    @PutMapping("/{id}/puntos")
	    public ResponseEntity<Usuario> agregarPuntos(
	            @PathVariable Long id,
	            @RequestParam int puntos
	    ) {
	        return ResponseEntity.ok(usuarioService.agregarPuntos(id, puntos));
	    }
	    
	    @GetMapping("/usuarios/{id}")
	    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
	        Usuario usuario = usuarioService.obtenerPerfil(id);
	        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
	        return ResponseEntity.ok(usuarioDTO);
	    }
	    

}
