package com.joaomk.seventh.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriarLivroDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 2, max = 200, message = "O título deve ter entre 2 e 200 caracteres")
    private String titulo;

    @NotBlank(message = "O(A) autor(a) é obrigatório")
    @Size(min = 2, max = 200, message = "O autor deve ter entre 2 e 200 caracteres")
    private String autor;

    @NotNull(message = "O ano de publicação é obrigatório")
    @Min( value = 1500, message = "O ano de publicação deve ser maior ou igual a 1500")
    @Max(value = 9999, message = "O ano de publicação deve ser menor ou igual a 9999")
    private Integer anoPublicacao;
}
