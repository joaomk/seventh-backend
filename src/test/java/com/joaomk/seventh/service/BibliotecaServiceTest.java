package com.joaomk.seventh.service;

import com.joaomk.seventh.dto.AlterarLivroDTO;
import com.joaomk.seventh.dto.CriarLivroDTO;
import com.joaomk.seventh.model.Livro;
import com.joaomk.seventh.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BibliotecaServiceTest {

    @InjectMocks
    private BibliotecaService bibliotecaService;

    @Mock
    private LivroRepository livroRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adicionarLivro_DeveSalvarELivrarLivro() {
        CriarLivroDTO livroDTO = new CriarLivroDTO("O Senhor dos Anéis", "J.R.R. Tolkien", 1954);
        Livro livroSalvo = new Livro(1L, "O Senhor dos Anéis", "J.R.R. Tolkien", 1954);

        when(livroRepository.save(any(Livro.class))).thenReturn(livroSalvo);

        Livro livro = bibliotecaService.adicionarLivro(livroDTO);

        assertNotNull(livro);
        assertEquals("O Senhor dos Anéis", livro.getTitulo());
        assertEquals("J.R.R. Tolkien", livro.getAutor());
        assertEquals(1954, livro.getAnoPublicacao());

        verify(livroRepository, times(1)).save(any(Livro.class));
    }

    @Test
    void listarLivros_DeveRetornarListaDeLivros() {
        Livro livro1 = new Livro(1L, "Livro 1", "Autor 1", 2000);
        Livro livro2 = new Livro(2L, "Livro 2", "Autor 2", 2010);

        when(livroRepository.findAll()).thenReturn(List.of(livro1, livro2));

        List<Livro> livros = bibliotecaService.listarLivros();

        assertNotNull(livros);
        assertEquals(2, livros.size());
        assertEquals("Livro 1", livros.get(0).getTitulo());
        assertEquals("Livro 2", livros.get(1).getTitulo());

        verify(livroRepository, times(1)).findAll();
    }

    @Test
    void buscarPorTitulo_DeveRetornarLivroQuandoEncontrado() {
        String titulo = "O Hobbit";
        Livro livro = new Livro(1L, titulo, "J.R.R. Tolkien", 1937);

        when(livroRepository.findByTitulo(titulo)).thenReturn(Optional.of(livro));

        Optional<Livro> resultado = bibliotecaService.buscarPorTitulo(titulo);

        assertTrue(resultado.isPresent());
        assertEquals(titulo, resultado.get().getTitulo());

        verify(livroRepository, times(1)).findByTitulo(titulo);
    }

    @Test
    void buscarPorTitulo_DeveRetornarVazioQuandoNaoEncontrado() {
        String titulo = "Livro Inexistente";
        when(livroRepository.findByTitulo(titulo)).thenReturn(Optional.empty());

        Optional<Livro> resultado = bibliotecaService.buscarPorTitulo(titulo);

        assertFalse(resultado.isPresent());
        verify(livroRepository, times(1)).findByTitulo(titulo);
    }

    @Test
    void atualizarLivro_DeveAtualizarELivrarLivro() {
        Long id = 1L;
        AlterarLivroDTO livroDTO = new AlterarLivroDTO("Novo Título", "Novo Autor", 2020);
        Livro livroExistente = new Livro(id, "Antigo Título", "Antigo Autor", 1990);

        when(livroRepository.findById(id)).thenReturn(Optional.of(livroExistente));
        when(livroRepository.save(any(Livro.class))).thenAnswer(i -> i.getArgument(0));

        Livro livroAtualizado = bibliotecaService.atualizarLivro(id, livroDTO);

        assertNotNull(livroAtualizado);
        assertEquals("Novo Título", livroAtualizado.getTitulo());
        assertEquals("Novo Autor", livroAtualizado.getAutor());
        assertEquals(2020, livroAtualizado.getAnoPublicacao());

        verify(livroRepository, times(1)).findById(id);
        verify(livroRepository, times(1)).save(livroExistente);
    }

    @Test
    void atualizarLivro_DeveLancarExcecaoQuandoNaoEncontrado() {
        Long id = 1L;
        AlterarLivroDTO livroDTO = new AlterarLivroDTO("Novo Título", "Novo Autor", 2020);

        when(livroRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> bibliotecaService.atualizarLivro(id, livroDTO));
        assertEquals("Livro não encontrado com o ID: " + id, exception.getMessage());

        verify(livroRepository, times(1)).findById(id);
        verify(livroRepository, never()).save(any(Livro.class));
    }

    @Test
    void excluirLivro_DeveRemoverLivroQuandoEncontrado() {
        Long id = 1L;

        when(livroRepository.existsById(id)).thenReturn(true);
        doNothing().when(livroRepository).deleteById(id);

        boolean resultado = bibliotecaService.excluirLivro(id);

        assertTrue(resultado);
        verify(livroRepository, times(1)).existsById(id);
        verify(livroRepository, times(1)).deleteById(id);
    }

    @Test
    void excluirLivro_DeveRetornarFalsoQuandoNaoEncontrado() {
        Long id = 1L;

        when(livroRepository.existsById(id)).thenReturn(false);

        boolean resultado = bibliotecaService.excluirLivro(id);

        assertFalse(resultado);
        verify(livroRepository, times(1)).existsById(id);
        verify(livroRepository, never()).deleteById(id);
    }
}