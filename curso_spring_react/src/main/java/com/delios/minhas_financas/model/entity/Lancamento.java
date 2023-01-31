package com.delios.minhas_financas.model.entity;

import com.delios.minhas_financas.enums.StatusLancamento;
import com.delios.minhas_financas.enums.TipoLancamento;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer mes;

    private Integer ano;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private BigDecimal valor;

    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class )
    private LocalDate dataCadastro;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;

}
