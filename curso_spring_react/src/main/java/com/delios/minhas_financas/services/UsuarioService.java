package com.delios.minhas_financas.services;

import com.delios.minhas_financas.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);
    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);
}
