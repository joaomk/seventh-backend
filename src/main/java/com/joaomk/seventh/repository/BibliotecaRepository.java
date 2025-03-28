package com.joaomk.seventh.repository;

import com.joaomk.seventh.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecaRepository extends JpaRepository<Livro, Long> {

    Livro findByTitulo(String titulo);
}
