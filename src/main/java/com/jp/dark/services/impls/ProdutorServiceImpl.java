package com.jp.dark.services.impls;

import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.exceptions.PersonaAlreadyExistsException;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.ProdutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;

@Service
@Slf4j
public class ProdutorServiceImpl implements ProdutorService {

    PersonaRepository repository;

    public ProdutorServiceImpl(PersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProdutorDTO save(ProdutorDTO produtor) {
        log.info("Salvando registro de produtor {}", produtor);
        //verifica se já existe um registro para o cpf informado
        if(this.cpfExists(produtor.getCpf())){
            throw new PersonaAlreadyExistsException();
        }

        Persona prd = this.toPersona(produtor);
        Persona persona = this.repository.save(prd);
        return toProdutorDTO(persona);
    }
    @Override
    public ProdutorDTO toProdutorDTO(Persona produtor) {

        String categoria;
        try{
            categoria = produtor.getCategoria().toString();
        }catch (NullPointerException ex){
            categoria = EnumCategoria.OUTROS.toString();
        }


        return ProdutorDTO.builder()
                .cpf(produtor.getCpf())
                .nome(produtor.getNome())
                .categoria(categoria)
                .cidade(produtor.getCidade())
                .endereco(produtor.getEndereco())
                .nascimento(produtor.getNascimento())
                .telefone(produtor.getTelefone())
                .build();
    }

    @Override
    public Persona toPersona(ProdutorDTO produtor) {

        EnumCategoria categoria;
        try{
            categoria = EnumCategoria.valueOf(produtor.getCategoria());
        }catch (NullPointerException ex){
            categoria = EnumCategoria.OUTROS;
        }

        return Persona.builder()
                .cpf(produtor.getCpf())
                .nome(produtor.getNome())
                .categoria(categoria)
                .cidade(produtor.getCidade())
                .endereco(produtor.getEndereco())
                .nascimento(produtor.getNascimento())
                .telefone(produtor.getTelefone())
                .build();
    }

    @Override
    public boolean cpfExists(@NotEmpty String cpf) {
        return repository.existsByCpf(cpf);
    }

}
