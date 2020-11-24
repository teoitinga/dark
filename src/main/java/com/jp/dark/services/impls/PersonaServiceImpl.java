package com.jp.dark.services.impls;

import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.PersonaService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository repository;

    public PersonaServiceImpl(PersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean cpfIsValid(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    @Override
    public boolean PersonaExists(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public List<Persona> toPersona(List<ProdutorMinDTO> produtores) {
        return produtores.stream().map(prd->toPersona(prd)).collect(Collectors.toList());
    }
    @Override
    public Persona toPersona(ProdutorMinDTO produtor) {
        String cpf;
        try{
            cpf = produtor.getCpf();
        }catch (NullPointerException ex){
            cpf = null;
        }
        String nome;
        try{
            nome= produtor.getNome();
        }catch (NullPointerException ex){
            nome = null;
        }
        return Persona.builder()
                .cpf(cpf)
                .nome(nome)
                .build();
    }

    @Override
    public ProdutorMinDTO save(ProdutorMinDTO produtor) {
        Persona save = this.toPersona(produtor);
        return this.toProdutorMinDTO(save);
    }

    @Override
    public ProdutorMinDTO toProdutorMinDTO(Persona persona) {
        return ProdutorMinDTO.builder()
                .cpf(persona.getCpf())
                .nome(persona.getNome())
                .build();
    }

    @Override
    public List<ProdutorMinDTO> toProdutorMinDTO(List<Persona> produtores) {
        return produtores.stream().map(prd->toProdutorMinDTO(prd)).collect(Collectors.toList());
    }
}
