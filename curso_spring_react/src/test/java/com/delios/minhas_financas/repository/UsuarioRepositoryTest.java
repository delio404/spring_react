package com.delios.minhas_financas.repository;


import com.delios.minhas_financas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.OptionalIntAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        //cenario
        Usuario usuario = criaUsuario();
        entityManager.persist(usuario);
        //acao / execucao
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificacao
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverCadastroComEmail() {
        //cenario

        //acao
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificacao

        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados() {
        //cenario
        Usuario usuario = criaUsuario();
        //acao
        Usuario usuarioSalvo = repository.save(usuario);

        //verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();

    }

    @Test
    public void deveBuscarUmUsuarioPorEmail() {
        //cenario
        Usuario usuario = criaUsuario();
        entityManager.persist(usuario);

        //verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");
        Assertions.assertThat(result.isPresent()).isTrue();

    }

    @Test
    public void deveRetonarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
        //verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");
        Assertions.assertThat(result.isPresent()).isFalse();

    }

    public static Usuario criaUsuario() {
        return Usuario
                .builder()
                .nome("usuario")
                .email("usuario@email.com")
                .senha("senha")
                .build();
    }


}


