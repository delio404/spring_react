package com.delios.minhas_financas.repository;


import com.delios.minhas_financas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//parei aki nessa classe esta dando erro  de conexao com o banco



@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveVerificarAExistenciaDeUmEmail(){
        //cenario
       Usuario usuario= Usuario.builder().nome("usuario").email("usuario@email.com").build();
       repository.save(usuario);
        //acao / execucao
        boolean result =repository.existsByEmail("usuario@email.com");

        //verificacao
        Assertions.assertThat(result).isTrue();
    }

}
