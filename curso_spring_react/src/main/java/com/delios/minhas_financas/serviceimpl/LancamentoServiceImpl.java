package com.delios.minhas_financas.serviceimpl;

import com.delios.minhas_financas.enums.StatusLancamento;
import com.delios.minhas_financas.exception.RegraNegocioException;
import com.delios.minhas_financas.model.entity.Lancamento;
import com.delios.minhas_financas.repository.LancamentoRepository;
import com.delios.minhas_financas.services.LancamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository lancamentoRepository;

    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validar(lancamento);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        lancamentoRepository.delete(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentofiltro) {
        Example example= Example.of(lancamentofiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return lancamentoRepository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);

    }

    @Override
    public void validar(Lancamento lancamento) {
        if (lancamento.getDescricao()== null  || lancamento.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Infome uma Descricao valida");
        }
        if (lancamento.getMes()== null || lancamento.getMes()< 1 || lancamento.getMes()> 12){
            throw new RegraNegocioException("Informe um Mes Valido. ");
        }
        if (lancamento.getAno()== null || lancamento.getAno().toString().length()!=4 ){
            throw new RegraNegocioException("Informe um Ano Valido. ");
        }
        if(lancamento.getUsuario()== null || lancamento.getUsuario().getId()== null){
            throw new RegraNegocioException("Informe um Usuario");
        }

        if(lancamento.getValor()== null || lancamento.getValor().compareTo(BigDecimal.ZERO)< 1){
            throw new RegraNegocioException("Informe um Valor valido");
        }
        if (lancamento.getTipo()==null){
            throw new RegraNegocioException("Infome um Tipo de Laçamento");
        }
    }
}
