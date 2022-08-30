package com.delios.minhas_financas.service;

import com.delios.minhas_financas.exception.RegraNegocioException;
import com.delios.minhas_financas.model.entity.Usuario;
import com.delios.minhas_financas.repository.UsuarioRepository;
import com.delios.minhas_financas.services.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepository repository;

    @Test(expected = Test.None.class )
    public void deveValidarEmail() {
      //cenario
        repository.deleteAll();

      //acao
        service.validarEmail("email@email.com");

    }

    @Test (expected = RegraNegocioException.class)
    public void  deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado(){
        //cenario
        Usuario usuario = Usuario.builder().nome("usuarlio").email("email@email.com").build();
        repository.save(usuario);

        //acao
        service.validarEmail("email@email.com");
    }
}
