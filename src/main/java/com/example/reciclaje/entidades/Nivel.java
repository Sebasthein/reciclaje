package com.example.reciclaje.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "niveles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("nombre")
    private String nombre; // Novato, Intermedio, Pro...
    private int puntosMinimos;

    @OneToMany(mappedBy = "nivel")
    private List<Usuario> usuarios;

}
