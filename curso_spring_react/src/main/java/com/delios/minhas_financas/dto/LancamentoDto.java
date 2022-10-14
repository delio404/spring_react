package com.delios.minhas_financas.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LancamentoDto {
    private Long id;

    private String descricao;

    private Integer mes;

    private Integer ano;

    private BigDecimal valor;

    private Long usuario;

    private String tipo;

    private String status;


}
