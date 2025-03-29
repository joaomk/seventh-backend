package com.joaomk.seventh.service;

import com.joaomk.seventh.dto.AlterarLivroDTO;
import com.joaomk.seventh.dto.CriarLivroDTO;
import com.joaomk.seventh.model.Livro;
import com.joaomk.seventh.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaService {


    @Autowired
    private LivroRepository livroRepository;


    private final List<Livro> livros = new ArrayList<>();

    /**
     * Adiciona um novo livro à biblioteca
     *
     * @param livro O livro a ser adicionado
     * @return O livro salvo com ID gerado
     */
    public Livro adicionarLivro(CriarLivroDTO livroDTO) {
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        return livroRepository.save(livro);
    }

    /**
     * Lista todos os livros da biblioteca
     * @return Lista de livros
     */
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    /**
     * Busca um livro pelo título
     * @param titulo O título do livro
     * @return Optional contendo o livro, se encontrado
     */
    public Optional<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTitulo(titulo);
    }

    /**
     * Busca um livro pelo ID
     * @param id O ID do livro
     * @return Optional contendo o livro, se encontrado
     */
    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    /**
     * Atualiza um livro existente
     * @param id ID do livro a ser atualizado
     * @param livroAtualizado Dados atualizados do livro
     * @return O livro atualizado
     * @throws RuntimeException se o livro não for encontrado
     */
    public Livro atualizarLivro(Long id, AlterarLivroDTO livroAtualizado) {
        Optional<Livro> livroExistente = livroRepository.findById(id);

        if (livroExistente.isPresent()) {
            Livro livro = livroExistente.get();

            livro.setTitulo(livroAtualizado.getTitulo());
            livro.setAutor(livroAtualizado.getAutor());
            livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());

            return livroRepository.save(livro);
        } else {
            throw new RuntimeException("Livro não encontrado com o ID: " + id);
        }
    }

    /**
     * Exclui um livro pelo ID
     * @param id ID do livro a ser excluído
     * @return true se o livro foi excluído, false se não foi encontrado
     */
    public boolean excluirLivro(Long id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
