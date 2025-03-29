package com.joaomk.seventh.integration;

import com.joaomk.seventh.dto.CriarLivroDTO;
import com.joaomk.seventh.model.Livro;
import com.joaomk.seventh.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LivroIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LivroRepository livroRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/biblioteca/livros";
    }

    @BeforeEach
    void limparBanco() {
        livroRepository.deleteAll();
    }

    @Test
    void adicionarLivro_DeveSalvarERetornarLivro() {
        CriarLivroDTO livroDTO = new CriarLivroDTO("O Senhor dos Anéis", "J.R.R. Tolkien", 1954);

        ResponseEntity<Livro> response = restTemplate.postForEntity(getBaseUrl(), livroDTO, Livro.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("O Senhor dos Anéis", response.getBody().getTitulo());
        assertEquals("J.R.R. Tolkien", response.getBody().getAutor());
        assertEquals(1954, response.getBody().getAnoPublicacao());

        List<Livro> livros = livroRepository.findAll();
        assertEquals(1, livros.size());
        assertEquals("O Senhor dos Anéis", livros.get(0).getTitulo());
    }

    @Test
    void listarLivros_DeveRetornarListaDeLivros() {
        Livro livro1 = new Livro(null, "Livro 1", "Autor 1", 2000);
        Livro livro2 = new Livro(null, "Livro 2", "Autor 2", 2010);
        livroRepository.saveAll(List.of(livro1, livro2));

        ResponseEntity<Livro[]> response = restTemplate.getForEntity(getBaseUrl(), Livro[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
        assertEquals("Livro 1", response.getBody()[0].getTitulo());
        assertEquals("Livro 2", response.getBody()[1].getTitulo());
    }

    @Test
    void buscarPorTitulo_DeveRetornarLivroQuandoEncontrado() {
        Livro livro = new Livro(null, "O Hobbit", "J.R.R. Tolkien", 1937);
        livroRepository.save(livro);

        String url = getBaseUrl() + "/titulo/O Hobbit";
        ResponseEntity<Livro> response = restTemplate.getForEntity(url, Livro.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("O Hobbit", response.getBody().getTitulo());
    }

    @Test
    void atualizarLivro_DeveAtualizarLivroExistente() {
        Livro livro = new Livro(null, "Antigo Título", "Antigo Autor", 1990);
        livro = livroRepository.save(livro);

        String url = getBaseUrl() + "/" + livro.getId();

        Livro livroAtualizado = new Livro(livro.getId(), "Novo Título", "Novo Autor", 2020);

        restTemplate.put(url, livroAtualizado);

        Livro livroDoBanco = livroRepository.findById(livro.getId()).orElse(null);
        assertNotNull(livroDoBanco);
        assertEquals("Novo Título", livroDoBanco.getTitulo());
        assertEquals("Novo Autor", livroDoBanco.getAutor());
        assertEquals(2020, livroDoBanco.getAnoPublicacao());
    }

    @Test
    void excluirLivro_DeveRemoverLivro() {
        Livro livro = new Livro(null, "Livro para Excluir", "Autor", 2000);
        livro = livroRepository.save(livro);

        String url = getBaseUrl() + "/" + livro.getId();

        restTemplate.delete(url);

        assertFalse(livroRepository.existsById(livro.getId()));
    }
}