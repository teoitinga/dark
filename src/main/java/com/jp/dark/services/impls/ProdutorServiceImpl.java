package com.jp.dark.services.impls;

import com.jp.dark.dtos.PersonaDTO;
import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.exceptions.PersonaAlreadyExistsException;
import com.jp.dark.exceptions.PersonaNotFoundException;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.ProdutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;

@Service
@Slf4j
public class ProdutorServiceImpl implements ProdutorService {

    PersonaRepository repository;

    PersonaService personaService;

    public ProdutorServiceImpl(PersonaRepository repository) {

        this.repository = repository;
        personaService = new PersonaServiceImpl(repository);
    }

    @Override
    public ProdutorDTO save(ProdutorDTO produtor) {
        //verifica se já existe um registro para o cpf informado
        if(this.cpfExists(produtor.getCpf())){
            throw new PersonaAlreadyExistsException();
        }

        Persona prd = this.personaService.toPersona(produtor);
        //Verifica e configura a permissao
        EnumPermissao permissao = EnumPermissao.CLIENTES;

        try{
                permissao = prd.getPermissao();
        }catch (Exception ex){

        }

        //Verifica e configura a categoria
        EnumCategoria categoria = EnumCategoria.AGRICULTOR_FAMILIAR;
        try{
            categoria = prd.getCategoria();
        }catch (Exception ex){

        }
        prd.setCategoria(categoria);
        prd.setPermissao(permissao);
        log.info("Salvando registro {}", prd);
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
    public boolean cpfExists(@NotEmpty String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public PersonaDTO update(String cpf, PersonaDTO dto) {

        //Busca o registro do produtor com o cpf informado
        Persona persona = this.findByCpf(cpf);

        dto.setCpf(persona.getCpf());

        Persona toSave = this.personaService.toPersona(dto);

        PersonaDTO saved = this.personaService.toPersonaDTO(repository.save(toSave));
        return saved;

    }

    @Override
    public Persona findByCpf(String cpf) {
        return repository.findByCpf(cpf).orElseThrow(
                ()-> new PersonaNotFoundException("Este produtor não foi encontrado no banco de dados."));
    }

}
