package com.delios.minhas_financas.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioDto {
    private String email;
    private String nome;
    private String senha;
}
