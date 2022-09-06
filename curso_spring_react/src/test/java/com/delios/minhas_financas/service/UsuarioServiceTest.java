package com.delios.minhas_financas.service;

import com.delios.minhas_financas.exception.RegraNegocioException;
import com.delios.minhas_financas.model.entity.Usuario;
import com.delios.minhas_financas.repository.UsuarioRepository;
import com.delios.minhas_financas.services.UsuarioService;
import com.delios.minhas_financas.services.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    UsuarioService service;

    @MockBean
    UsuarioRepository repository;

    @Before
    public void setup(){
        service=new UsuarioServiceImpl(repository);
    }

    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso(){
        //cenario
        String email= "email@email.com";
        String senha= "senha";
        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //acao
        Usuario result = service.autenticar(email,senha);

        //verificacao
        Assertions.assertThat(result).isNotNull();




    }

    @Test(expected = Test.None.class )
    public void deveValidarEmail() {
      //cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

      //acao
        service.validarEmail("email@email.com");

    }

    @Test (expected = RegraNegocioException.class)
    public void  deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado(){
        //cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
        //acao
        service.validarEmail("email@email.com");
    }
}
