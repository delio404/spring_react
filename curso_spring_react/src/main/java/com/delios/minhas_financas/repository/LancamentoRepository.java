package com.delios.minhas_financas.repository;

import com.delios.minhas_financas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository  extends JpaRepository<Lancamento,Long> {
}
