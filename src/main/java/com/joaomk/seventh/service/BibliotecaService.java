package com.joaomk.seventh.service;

import com.joaomk.seventh.model.Livro;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaService {
    private final List<Livro> livros = new ArrayList<>();

    public String adicionarLivro(Livro livro) {
        livros.add(livro);
        return "Livro adicionado com sucesso!";
    }

    public List<Livro> listarLivros() {
        return livros;
    }

    public Optional<Livro> buscarPorTitulo(String titulo) {
        return livros.stream()
                .filter(livro -> livro.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }
}
