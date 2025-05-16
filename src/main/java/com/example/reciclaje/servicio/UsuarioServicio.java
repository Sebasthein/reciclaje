package com.example.reciclaje.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.reciclaje.entidades.Nivel;
import com.example.reciclaje.entidades.Usuario;
import com.example.reciclaje.repositorio.NivelRepositorio;
import com.example.reciclaje.repositorio.UsuarioRepositorio;
import com.example.reciclaje.seguridad.JwtUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final JwtUtils jwtUtils;
	
	 private final UsuarioRepositorio usuarioRepository;
	    private final NivelRepositorio nivelRepository;

	    public Usuario registrarUsuario(Usuario usuario) {
	        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
	            throw new IllegalArgumentException("El correo ya está registrado.");
	        }

	        // Inicializamos los puntos a 0
	        usuario.setPuntos(0);

	        // Asignamos el nivel basado en los puntos del usuario
	        Nivel nivel = obtenerNivelPorPuntos(usuario.getPuntos());
	        usuario.setNivel(nivel);

	        // Guardamos el usuario en la base de datos
	        return usuarioRepository.save(usuario);
	    }

	    public String iniciarSesionYGenerarToken(String email, String password) {
	        Usuario usuario = usuarioRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        if (!usuario.getPassword().equals(password)) {
	            throw new RuntimeException("Contraseña incorrecta");
	        }

	        return jwtUtils.generarToken(usuario.getEmail());
	    }

	    public Usuario obtenerPerfil(Long id) {
	        return usuarioRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	    }
	    
	    public Usuario obtenerPerfilPorEmail(String email) {
	        return usuarioRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	    }
	    
	    // Método para asignar nivel según puntos
	    private Nivel obtenerNivelPorPuntos(int puntos) {
	    	  List<Nivel> niveles = nivelRepository.findAll()
	    		        .stream()
	    		        .sorted((n1, n2) -> Integer.compare(n2.getPuntosMinimos(), n1.getPuntosMinimos())) // orden descendente
	    		        .toList();

	    		    for (Nivel nivel : niveles) {
	    		        if (puntos >= nivel.getPuntosMinimos()) {
	    		            return nivel;
	    		        }
	    		    }

	    		    return niveles.isEmpty() ? null : niveles.get(niveles.size() - 1); // el nivel más bajo
	    }
	
	    public Usuario agregarPuntos(Long usuarioId, int puntosGanados) {
	        Usuario usuario = usuarioRepository.findById(usuarioId)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        usuario.setPuntos(usuario.getPuntos() + puntosGanados);

	        // Actualiza el nivel si corresponde
	        actualizarNivel(usuario);

	        return usuarioRepository.save(usuario);
	    }

	    // Método para actualizar nivel después de modificar puntos
	    public void actualizarNivel(Usuario usuario) {
	        usuario.setNivel(obtenerNivelPorPuntos(usuario.getPuntos()));
	        usuarioRepository.save(usuario);
	    }

}
