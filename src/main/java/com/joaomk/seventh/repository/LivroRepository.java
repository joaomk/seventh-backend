package com.joaomk.seventh.repository;

import com.joaomk.seventh.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Método personalizado para buscar um livro pelo título
    Optional<Livro> findByTitulo(String titulo);
}