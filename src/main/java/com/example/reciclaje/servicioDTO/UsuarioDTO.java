package com.example.reciclaje.servicioDTO;

import com.example.reciclaje.entidades.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	
	private Long id;
    private String nombre;
    private String email;
    private int puntos;
    private String nombreNivel; // Nombre del nivel

    
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.puntos = usuario.getPuntos();
        this.nombreNivel = usuario.getNivel().getNombre(); // Asignamos el nombre del nivel
    }
}
