package com.delios.minhas_financas.apiresource;
import com.delios.minhas_financas.dto.LancamentoDto;
import com.delios.minhas_financas.enums.StatusLancamento;
import com.delios.minhas_financas.enums.TipoLancamento;
import com.delios.minhas_financas.exception.RegraNegocioException;
import com.delios.minhas_financas.model.entity.Lancamento;
import com.delios.minhas_financas.model.entity.Usuario;
import com.delios.minhas_financas.services.LancamentoService;
import com.delios.minhas_financas.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

    private LancamentoService service;
    private UsuarioService usuarioService;

    public LancamentoResource(LancamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity salvar (@RequestBody dto ){


    }
    private Lancamento converter(LancamentoDto dto){
        Lancamento lancamento= new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());
        Usuario usuario=usuarioService
                .obterPorId(dto.getUsuario())
                .orElseThrow(() ->new RegraNegocioException("Usuario nao emcontrado para o Id informado."));
        lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        lancamento.setUsuario(usuario);
        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        return lancamento;
    }
}
