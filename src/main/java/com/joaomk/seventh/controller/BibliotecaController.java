package com.joaomk.seventh.controller;

import com.joaomk.seventh.dto.AlterarLivroDTO;
import com.joaomk.seventh.dto.CriarLivroDTO;
import com.joaomk.seventh.model.Livro;
import com.joaomk.seventh.service.BibliotecaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/biblioteca/livros")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @PostMapping
    public ResponseEntity<Livro> adicionarLivro(@Valid @RequestBody CriarLivroDTO livroDTO) {
        Livro livroSalvo = bibliotecaService.adicionarLivro(livroDTO);
        return new ResponseEntity<>(livroSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = bibliotecaService.listarLivros();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Livro> buscarPorTitulo(@PathVariable String titulo) {
        Optional<Livro> livro = bibliotecaService.buscarPorTitulo(titulo);
        return livro.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
               .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @RequestBody AlterarLivroDTO livro) {
        try {
            Livro livroAtualizado = bibliotecaService.atualizarLivro(id, livro);
            return new ResponseEntity<>(livroAtualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable Long id) {
        boolean removido = bibliotecaService.excluirLivro(id);
        if (removido) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
