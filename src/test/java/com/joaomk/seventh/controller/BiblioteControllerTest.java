package com.joaomk.seventh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaomk.seventh.dto.AlterarLivroDTO;
import com.joaomk.seventh.dto.CriarLivroDTO;
import com.joaomk.seventh.model.Livro;
import com.joaomk.seventh.service.BibliotecaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BibliotecaController.class)
class BibliotecaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BibliotecaService bibliotecaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void adicionarLivro_DeveRetornarLivroCriado() throws Exception {
        CriarLivroDTO livroDTO = new CriarLivroDTO("O Hobbit", "J.R.R. Tolkien", 1937);
        Livro livroSalvo = new Livro(1L, "O Hobbit", "J.R.R. Tolkien", 1937);

        Mockito.when(bibliotecaService.adicionarLivro(any(CriarLivroDTO.class))).thenReturn(livroSalvo);

        mockMvc.perform(post("/biblioteca/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(livroDTO))) // Converte o objeto para JSON
                .andExpect(status().isCreated()) // Código HTTP 201
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("O Hobbit"))
                .andExpect(jsonPath("$.autor").value("J.R.R. Tolkien"))
                .andExpect(jsonPath("$.anoPublicacao").value(1937));
    }

    @Test
    void listarLivros_DeveRetornarListaDeLivros() throws Exception {
        Livro livro1 = new Livro(1L, "O Hobbit", "J.R.R. Tolkien", 1937);
        Livro livro2 = new Livro(2L, "O Senhor dos Anéis", "J.R.R. Tolkien", 1954);

        Mockito.when(bibliotecaService.listarLivros()).thenReturn(Arrays.asList(livro1, livro2));

        mockMvc.perform(get("/biblioteca/livros")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Código HTTP 200
                .andExpect(jsonPath("$.length()").value(2)) // Valida o número de elementos na lista
                .andExpect(jsonPath("$[0].titulo").value("O Hobbit"))
                .andExpect(jsonPath("$[1].titulo").value("O Senhor dos Anéis"));
    }

    @Test
    void buscarPorTitulo_DeveRetornarLivroQuandoEncontrado() throws Exception {
        Livro livro = new Livro(1L, "O Hobbit", "J.R.R. Tolkien", 1937);
        Mockito.when(bibliotecaService.buscarPorTitulo("O Hobbit")).thenReturn(Optional.of(livro));

        mockMvc.perform(get("/biblioteca/livros/titulo/O Hobbit")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Código HTTP 200
                .andExpect(jsonPath("$.titulo").value("O Hobbit"))
                .andExpect(jsonPath("$.autor").value("J.R.R. Tolkien"))
                .andExpect(jsonPath("$.anoPublicacao").value(1937));
    }

    @Test
    void buscarPorTitulo_DeveRetornar404QuandoNaoEncontrado() throws Exception {
        Mockito.when(bibliotecaService.buscarPorTitulo("Inexistente")).thenReturn(Optional.empty());

        mockMvc.perform(get("/biblioteca/livros/titulo/Inexistente")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Código HTTP 404
    }

    @Test
    void atualizarLivro_DeveRetornarLivroAtualizado() throws Exception {
        AlterarLivroDTO livroDTO = new AlterarLivroDTO("O Hobbit (Edição Atualizada)", "J.R.R. Tolkien", 2023);
        Livro livroAtualizado = new Livro(1L, "O Hobbit (Edição Atualizada)", "J.R.R. Tolkien", 2023);

        Mockito.when(bibliotecaService.atualizarLivro(eq(1L), any(AlterarLivroDTO.class))).thenReturn(livroAtualizado);

        mockMvc.perform(put("/biblioteca/livros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(livroDTO)))
                .andExpect(status().isOk()) // Código HTTP 200
                .andExpect(jsonPath("$.titulo").value("O Hobbit (Edição Atualizada)"))
                .andExpect(jsonPath("$.anoPublicacao").value(2023));
    }

    @Test
    void atualizarLivro_DeveRetornar404QuandoNaoEncontrado() throws Exception {
        AlterarLivroDTO livroDTO = new AlterarLivroDTO("Inexistente", "Autor Desconhecido", 2000);

        Mockito.when(bibliotecaService.atualizarLivro(eq(99L), any(AlterarLivroDTO.class))).thenThrow(new RuntimeException("Livro não encontrado"));

        mockMvc.perform(put("/biblioteca/livros/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(livroDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void excluirLivro_DeveRetornarNoContentQuandoExcluido() throws Exception {
        Mockito.when(bibliotecaService.excluirLivro(1L)).thenReturn(true);

        mockMvc.perform(delete("/biblioteca/livros/1"))
                .andExpect(status().isNoContent()); // Código HTTP 204
    }

    @Test
    void excluirLivro_DeveRetornar404QuandoNaoEncontrado() throws Exception {
        Mockito.when(bibliotecaService.excluirLivro(99L)).thenReturn(false);

        mockMvc.perform(delete("/biblioteca/livros/99"))
                .andExpect(status().isNotFound()); // Código HTTP 404
    }
}