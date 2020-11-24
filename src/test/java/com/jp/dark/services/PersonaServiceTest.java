package com.jp.dark.services;

import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.factory.ProdutorFactory;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.impls.PersonaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PersonaServiceTest {
    PersonaService service;

    @MockBean
    PersonaRepository repository;

    @BeforeEach
    public void setup(){
        this.service = new PersonaServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve retornar true caso o CPF seja valido")
    public void cpfIsValidTest(){
        String cpf = "04459471604";

        boolean isValid = this.service.cpfIsValid(cpf);

        //verificação
        assertThat(isValid).isTrue();

    }
    @Test
    @DisplayName("Deve retornar false caso o CPF seja invalido")
    public void cpfIsInValidTest(){
        String cpf = "11111111111";

        boolean isValid = this.service.cpfIsValid(cpf);

        //verificação
        assertThat(isValid).isFalse();

    }
    @Test
    @DisplayName("Deve retornar true caso o exista registro desta pessoa no banco de dados.")
    public void PersonaExistsTest(){
        String cpf = "04459471604";

        Mockito.when(repository.existsByCpf(cpf)).thenReturn(true);

        boolean personaExists = this.service.PersonaExists(cpf);

        assertThat(personaExists).isTrue();
    }
    @Test
    @DisplayName("Deve retornar false caso não exista registro desta pessoa no banco de dados.")
    public void PersonaNoExistsTest(){
        String cpf = "04459471604";

        Mockito.when(repository.existsByCpf(cpf)).thenReturn(false);

        boolean personaExists = this.service.PersonaExists(cpf);

        assertThat(personaExists).isFalse();
    }
    @Test
    @DisplayName("Deve verificar o upcast de uma lista de ProdutorMinDTO para Persona")
    public void toPersonaTest(){
        List<ProdutorMinDTO> produtores = ProdutorFactory.createList5ValidProdutors();

        List<Persona> pessoal = this.service.toPersona(produtores);

        assertThat(pessoal).hasSize(5);
        assertThat(pessoal).isNotEmpty();
        assertThat(pessoal).isNotNull();
        assertThat(pessoal.get(0).getNome()).isEqualTo(produtores.get(0).getNome());
        assertThat(pessoal.get(0).getCpf()).isEqualTo(produtores.get(0).getCpf());
        assertThat(pessoal.get(0).getCategoria()).isNull();
        assertThat(pessoal.get(0).getCidade()).isNull();
        assertThat(pessoal.get(0).getEndereco()).isNull();
        assertThat(pessoal.get(0).getNascimento()).isNull();
        assertThat(pessoal.get(0).getTelefone()).isNull();
        assertThat(pessoal.get(0).getPermissao()).isNull();
        assertThat(pessoal.get(0).getSenha()).isNull();

        assertThat(pessoal.get(4).getNome()).isEqualTo(produtores.get(4).getNome());
        assertThat(pessoal.get(4).getCpf()).isEqualTo(produtores.get(4).getCpf());
        assertThat(pessoal.get(4).getCategoria()).isNull();
        assertThat(pessoal.get(4).getCidade()).isNull();
        assertThat(pessoal.get(4).getEndereco()).isNull();
        assertThat(pessoal.get(4).getNascimento()).isNull();
        assertThat(pessoal.get(4).getTelefone()).isNull();
        assertThat(pessoal.get(4).getPermissao()).isNull();
        assertThat(pessoal.get(4).getSenha()).isNull();
    }
    @Test
    @DisplayName("Deve verificar o downcast de uma lista de Persona para ProdutorMinDTO")
    public void toProdutorMinDTOTest(){
        List<Persona> produtores = ProdutorFactory.createList5ValidPersona();

        List<ProdutorMinDTO> pessoal = this.service.toProdutorMinDTO(produtores);

        assertThat(pessoal).hasSize(5);
        assertThat(pessoal).isNotEmpty();
        assertThat(pessoal).isNotNull();
        assertThat(pessoal.get(0).getNome()).isEqualTo(produtores.get(0).getNome());
        assertThat(pessoal.get(0).getCpf()).isEqualTo(produtores.get(0).getCpf());

        assertThat(pessoal.get(4).getNome()).isEqualTo(produtores.get(4).getNome());
        assertThat(pessoal.get(4).getCpf()).isEqualTo(produtores.get(4).getCpf());

    }
    @Test
    @DisplayName("Deve verificar o downcast de Persona para ProdutorMinDTO")
    public void toProdutorMinDTOSingleTest(){
        Persona produtores = ProdutorFactory.createNewPersona();

        ProdutorMinDTO pessoal = this.service.toProdutorMinDTO(produtores);

        assertThat(pessoal).isNotNull();
        assertThat(pessoal.getNome()).isEqualTo(produtores.getNome());
        assertThat(pessoal.getCpf()).isEqualTo(produtores.getCpf());

    }

    @Test
    @DisplayName("Deve verificar o upcast de ProdutorMinDTO para Persona")
    public void toPersonaSingleTest(){
        ProdutorMinDTO produtor = ProdutorFactory.createProdutorMinDto();

        Persona persona = this.service.toPersona(produtor);

        assertThat(persona).isNotNull();
        assertThat(persona.getNome()).isEqualTo(produtor.getNome());
        assertThat(persona.getCpf()).isEqualTo(produtor.getCpf());
        assertThat(persona.getCategoria()).isNull();
        assertThat(persona.getCidade()).isNull();
        assertThat(persona.getEndereco()).isNull();
        assertThat(persona.getNascimento()).isNull();
        assertThat(persona.getTelefone()).isNull();
        assertThat(persona.getPermissao()).isNull();
        assertThat(persona.getSenha()).isNull();

    }
}
