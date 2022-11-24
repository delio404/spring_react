package com.delios.minhas_financas.apiresource;


import com.delios.minhas_financas.dto.UsuarioDto;
import com.delios.minhas_financas.exception.ErroAutenticacao;
import com.delios.minhas_financas.exception.RegraNegocioException;
import com.delios.minhas_financas.model.entity.Usuario;
import com.delios.minhas_financas.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users{id}")
@RestController
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioService usuarioService;
    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDto dto) {
        try {
            Usuario usuarioAutenticado= usuarioService.autenticar(dto.getEmail(),dto.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDto dto){
        Usuario usuario=Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha()).build();
        try{
            Usuario usuarioSalvo= usuarioService.salvarUsuario(usuario);
            return  new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
