package com.example.reciclaje.servicio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.reciclaje.entidades.EstadoReciclaje;
import com.example.reciclaje.entidades.Material;
import com.example.reciclaje.entidades.Reciclaje;
import com.example.reciclaje.entidades.Usuario;
import com.example.reciclaje.repositorio.MaterialRepositorio;
import com.example.reciclaje.repositorio.ReciclajeRepositorio;
import com.example.reciclaje.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReciclajeServicio {
	 private final ReciclajeRepositorio reciclajeRepository;
	    private final UsuarioRepositorio usuarioRepository;
	    private final MaterialRepositorio materialRepository;

	    public Reciclaje registrarReciclaje(String email, Long materialId, String fotoUrl) {
	        Usuario usuario = usuarioRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        Material material = materialRepository.findById(materialId)
	            .orElseThrow(() -> new RuntimeException("Material no encontrado"));

	        Reciclaje reciclaje = new Reciclaje();
	        reciclaje.setUsuario(usuario);
	        reciclaje.setMaterial(material);
	        reciclaje.setFotoUrl(fotoUrl);
	        reciclaje.setEstado(EstadoReciclaje.PENDIENTE);
	        reciclaje.setValidado(false);
	        reciclaje.setFecha(LocalDate.now());

	        // si deseas, suma puntos aquí también

	        return reciclajeRepository.save(reciclaje);
	    }

	    public List<Reciclaje> obtenerReciclajesPorUsuario(Long usuarioId) {
	        Usuario usuario = usuarioRepository.findById(usuarioId)
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        return reciclajeRepository.findByUsuario(usuario);
	    }

	    public Reciclaje validarReciclaje(Long reciclajeId) {
	        Reciclaje reciclaje = reciclajeRepository.findById(reciclajeId)
	            .orElseThrow(() -> new RuntimeException("Reciclaje no encontrado"));

	        reciclaje.setValidado(true);

	        // Sumar puntos al usuario
	        Usuario usuario = reciclaje.getUsuario();
	        usuario.setPuntos(usuario.getPuntos() + 10); // Por ejemplo

	        usuarioRepository.save(usuario);
	        return reciclajeRepository.save(reciclaje);
	    }

}
