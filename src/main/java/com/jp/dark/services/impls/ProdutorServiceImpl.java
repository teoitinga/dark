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
import java.time.LocalDate;

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
        if(this.cpfExists(produtor.getCpf()) || produtor.getCpf()==null){
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
        EnumCategoria categoria = EnumCategoria.OUTROS;
        try{
            categoria = prd.getCategoria();
        }catch (Exception ex){

        }
        prd.setCategoria(categoria);
        prd.setPermissao(permissao);

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

        String cpf = null;
        try{
            cpf = produtor.getCpf();
        }catch (NullPointerException ex){
            cpf = null;
        }
        String nome = null;
        try{
            nome = produtor.getNome();
        }catch (NullPointerException ex){
        }
        String cidade = null;
        try{
            cidade = produtor.getCidade();
        }catch (NullPointerException ex){

        }
        String endereco  = null;
        try{
            endereco = produtor.getEndereco();
        }catch (NullPointerException ex){

        }
        LocalDate nascimento = null;
        try{
             nascimento = produtor.getNascimento();
        }catch (NullPointerException ex){

        }

        String telefone = null;
        try{
            telefone = produtor.getTelefone();
        }catch (NullPointerException ex){

        }

        String cep = null;
        try{
            cep = produtor.getCep();
        }catch (NullPointerException ex){

        }
        return ProdutorDTO.builder()
                .cpf(cpf)
                .nome(nome)
                .categoria(categoria)
                .cidade(cidade)
                .endereco(endereco)
                .nascimento(nascimento)
                .telefone(telefone)
                .cep(cep)
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

        String cpfInfo = null;
        try{
            cpfInfo = persona.getCpf();
            dto.setCpf(cpfInfo);
        }catch (NullPointerException ex){

        }

        Persona toSave = this.personaService.toPersona(dto);

        toSave = repository.save(toSave);

        PersonaDTO saved = this.personaService.toPersonaDTO(toSave);
        return saved;

    }

    @Override
    public Persona findByCpf(String cpf) {
        return repository.findByCpf(cpf).orElseThrow(
                ()-> new PersonaNotFoundException("Este produtor não foi encontrado no banco de dados."));
    }

}
