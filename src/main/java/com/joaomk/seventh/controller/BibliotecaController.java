package com.joaomk.seventh.controller;

import com.joaomk.seventh.model.Livro;
import com.joaomk.seventh.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @PostMapping("/adicionar")
    public String adicionarLivro(@RequestBody Livro livro) {
        return bibliotecaService.adicionarLivro(livro);
    }

    @GetMapping("/listar")
    public List<Livro> listarLivros() {
        return bibliotecaService.listarLivros();
    }

    @GetMapping("/buscar")
    public Optional<Livro> buscarPorTitulo(@RequestParam String titulo) {
        return bibliotecaService.buscarPorTitulo(titulo);
    }
}
