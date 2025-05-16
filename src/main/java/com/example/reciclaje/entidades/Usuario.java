package com.example.reciclaje.entidades;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nombre;
	    private String email;
	    private String password;
	    private int puntos;

	    @ManyToOne(fetch = FetchType.EAGER) 
	    @JoinColumn(name = "nivel_id")
	    private Nivel nivel;

	    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	    private List<Reciclaje> reciclajes;

	    @ManyToMany
	    @JoinTable(
	        name = "usuario_logros",
	        joinColumns = @JoinColumn(name = "usuario_id"),
	        inverseJoinColumns = @JoinColumn(name = "logro_id")
	    )
	    private Set<Logro> logros;
}
