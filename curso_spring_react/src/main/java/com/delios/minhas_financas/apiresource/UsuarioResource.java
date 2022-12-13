package com.delios.minhas_financas.apiresource;


import com.delios.minhas_financas.dto.UsuarioDto;
import com.delios.minhas_financas.exception.ErroAutenticacao;
import com.delios.minhas_financas.exception.RegraNegocioException;
import com.delios.minhas_financas.model.entity.Usuario;
import com.delios.minhas_financas.services.LancamentoService;
import com.delios.minhas_financas.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

@RequestMapping("/api/users{id}")
@RestController
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioService usuarioService;
    private  final LancamentoService lancamentoService;
    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody UsuarioDto dto) {
        try {
            Usuario usuarioAutenticado= usuarioService.autenticar(dto.getEmail(),dto.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody UsuarioDto dto){
        Usuario usuario=Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha()).build();
        try{
            Usuario usuarioSalvo= usuarioService.salvarUsuario(usuario);
            return  new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("{id}/saldo")
    public ResponseEntity<?> obterSaldo(@PathVariable ("id") Long id){
        Optional<Usuario> usuario=usuarioService.obterPorId(id);
        if(!usuario.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BigDecimal saldo= lancamentoService.obterSaldoPorUsuario(id);

        return ResponseEntity.ok(saldo);
    }


}
