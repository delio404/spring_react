package com.delios.minhas_financas.services;

import com.delios.minhas_financas.exception.ErroAutenticacao;
import com.delios.minhas_financas.exception.RegraNegocioException;
import com.delios.minhas_financas.model.entity.Usuario;
import com.delios.minhas_financas.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UsuarioServiceImpl implements UsuarioService {
    //@Autowired
    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario=repository.findByEmail(email);
        if(!usuario.isPresent()){
            throw new ErroAutenticacao("Usuario nao encontrado");
        }
        if(usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("senha invalida");
        }
        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail((usuario.getEmail()));
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe=repository.existsByEmail(email);
        if(existe){
            throw new RegraNegocioException("Ja existe um usuario cadastrado com  este email.");
        }
    }
}
