package com.joaomk.seventh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlterarLivroDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 2, max = 200, message = "O título deve ter entre 2 e 200 caracteres")
    private String titulo;

    @NotBlank(message = "O autor é obrigatório")
    @Size(min = 2, max = 200, message = "O autor deve ter entre 2 e 200 caracteres")
    private String autor;

    @NotNull(message = "O ano de publicação é obrigatório")
    private Integer anoPublicacao;
}
