package com.delios.minhas_financas.apiresource;
import com.delios.minhas_financas.dto.LancamentoDto;
import com.delios.minhas_financas.enums.StatusLancamento;
import com.delios.minhas_financas.enums.TipoLancamento;
import com.delios.minhas_financas.exception.RegraNegocioException;
import com.delios.minhas_financas.model.entity.Lancamento;
import com.delios.minhas_financas.model.entity.Usuario;
import com.delios.minhas_financas.services.LancamentoService;
import com.delios.minhas_financas.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

    private LancamentoService service;
    private UsuarioService usuarioService;

    public LancamentoResource(LancamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity salvar (@RequestBody LancamentoDto dto ){
        try {
            Lancamento entidade = converter(dto);
            entidade= service.salvar(entidade);
            return  new ResponseEntity(entidade, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity buscar(
//            poderia simplifiacar dessa maneira o requestParam detalhes nesse metodo todos seriam opcionais
//            @RequestParam java.util.Map<String, String> params
//            ){
            @RequestParam (value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam("usario") Long idUsuario
            ){
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);

        Optional<Usuario>usuario=usuarioService.obterPorId(idUsuario);
        if(usuario.isPresent()){
            return ResponseEntity.badRequest().body("Nao foi possivel realizar a consulta. Usuario nao encontrado para Id informado");
        }else {
            lancamentoFiltro.setUsuario(usuario.get());
        }
        List<Lancamento> lancamentos= service.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);
    }
                                 ){

    }


    @PutMapping("id")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDto dto){
        return service.obterPorId(id).map(entity -> {
            try {
            Lancamento lancamento = converter(dto);
            lancamento.setId(entity.getId());
            service.atualizar(lancamento);
            return ResponseEntity.ok(lancamento);
                }catch (RegraNegocioException e){
                    return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() ->
                new ResponseEntity("Lancamento nao encontrado na base de dados",HttpStatus.BAD_REQUEST));
    }

    public ResponseEntity deletar (@PathVariable("id") Long id){
        return service.obterPorId(id).map(entidade ->{
            service.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(()->
            new ResponseEntity("Lancamento nao encontrado na base de dados", HttpStatus.BAD_REQUEST));
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
