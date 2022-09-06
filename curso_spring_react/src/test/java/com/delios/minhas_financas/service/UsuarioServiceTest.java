package com.delios.minhas_financas.service;

import com.delios.minhas_financas.exception.RegraNegocioException;
import com.delios.minhas_financas.model.entity.Usuario;
import com.delios.minhas_financas.repository.UsuarioRepository;
import com.delios.minhas_financas.services.UsuarioService;
import com.delios.minhas_financas.services.UsuarioServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    UsuarioService service;
    UsuarioRepository repository;

    @Before
    public void setup(){
        repository = Mockito.mock(UsuarioRepository.class);
        service=new UsuarioServiceImpl(repository);
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
