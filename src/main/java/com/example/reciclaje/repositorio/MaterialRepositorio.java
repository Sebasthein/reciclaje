package com.example.reciclaje.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reciclaje.entidades.Material;

public interface MaterialRepositorio extends JpaRepository<Material, Long> {
    boolean existsByNombre(String nombre);
}
