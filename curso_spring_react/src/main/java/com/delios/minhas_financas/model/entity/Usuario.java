package com.delios.minhas_financas.model.entity;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
@Builder
@Data
@Entity
public class Usuario {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;


}
