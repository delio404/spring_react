package com.delios.minhas_financas.services;

import com.delios.minhas_financas.enums.StatusLancamento;
import com.delios.minhas_financas.model.entity.Lancamento;

import java.util.List;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> buscar(Lancamento lancamentofiltro);

    void atualizarStatus(Lancamento lancamento, StatusLancamento status);
}
