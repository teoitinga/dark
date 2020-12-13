package com.jp.dark.services.impls;

import com.jp.dark.dtos.PersonaDTO;
import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.dtos.UserDTO;
import com.jp.dark.exceptions.PersonaAlreadyExistsException;
import com.jp.dark.exceptions.PersonaNotFoundException;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.PersonaService;
import com.jp.dark.utils.GeraCpfCnpj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository repository;

    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.repository = personaRepository;
    }

    @Override
    public List<ProdutorMinDTO> toListProdutorMinDTO(List<Persona> produtores) {
        return produtores.stream().map(prd->toProdutorMinDTO(prd)).collect(Collectors.toList());
    }
    @Override
    public ProdutorMinDTO toProdutorMinDTO(Persona persona) {
        return ProdutorMinDTO.builder()
                .nome(persona.getNome())
                .cpf(persona.getCpf())
                .build();
    }

    @Override
    public List<Persona> toListPersona(List<ProdutorMinDTO> produtores) {
        return produtores.stream().map(prd->toPersona(prd)).collect(Collectors.toList());
    }

    @Override
    public Persona toPersona(ProdutorMinDTO dto) {

        String nome;
        try {
            nome = dto.getNome();
        } catch (NullPointerException ex) {
            nome = null;
        }

        String cpf;
        try {
            cpf = dto.getCpf();
        } catch (NullPointerException ex) {
            cpf = null;
        }
        return Persona.builder()
                .nome(nome)
                .cpf(cpf)
                .permissao(EnumPermissao.CLIENTES)
                .categoria(EnumCategoria.OUTROS)
                .build();
    }

    @Override
    public ProdutorDTO save(ProdutorDTO dto) {
        /*
        1- Verifica se o produtor já está registrado
         */
        boolean exists = this.personaExists(dto.getCpf());

        if(exists){
            throw new PersonaAlreadyExistsException();
        }

        String categoria;
        try{
            categoria = dto.getCategoria();
        }catch (NullPointerException ex){
            categoria = EnumCategoria.OUTROS.toString();
        }
        dto.setCategoria(categoria);

        Persona produtor = this.toPersona(dto);


        Persona saved = this.repository.save(produtor);

        return toProdutorDTO(saved);
    }

    @Override
    public ProdutorDTO toProdutorDTO(Persona dto) {

        String categoria = dto.getCategoria().toString();

        return ProdutorDTO.builder()
                .cpf(dto.getCpf())
                .nome(dto.getNome())
                .telefone(dto.getTelefone())
                .nascimento(dto.getNascimento())
                .categoria(categoria)
                .endereco(dto.getEndereco())
                .cidade(dto.getCidade())
                .cep(dto.getCep())
                .build();
    }

    @Override
    public Persona toPersona(ProdutorDTO dto) {
        return Persona.builder()
                .cpf(dto.getCpf())
                .nome(dto.getNome())
                .telefone(dto.getTelefone())
                .nascimento(dto.getNascimento())
                .categoria(EnumCategoria.valueOf(dto.getCategoria()))
                .permissao(EnumPermissao.CLIENTES)
                .endereco(dto.getEndereco())
                .cidade(dto.getCidade())
                .cep(dto.getCep())
                .build();
    }

    @Override
    public PersonaDTO update(String cpf, PersonaDTO dto) {
        return null;
    }

    @Override
    public boolean cpfIsValid(String cpf) {
        GeraCpfCnpj geraCpfCnpj = new GeraCpfCnpj();
        return geraCpfCnpj.isCPF(cpf);
    }

    @Override
    public boolean personaExists(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public List<Persona> toPersona(List<ProdutorMinDTO> produtores) {
        return produtores.stream().map(prd->toPersona(prd)).collect(Collectors.toList());
    }

    @Override
    public List<ProdutorMinDTO> toProdutorMinDTO(List<Persona> produtores) {
        return produtores.stream().map(prd->toProdutorMinDTO(prd)).collect(Collectors.toList());
    }

    @Override
    public Persona save(ProdutorMinDTO produtor) {
        Persona prd = this.toPersona(produtor);
        return this.repository.save(prd);
    }

    @Override
    public Persona findByCpf(String cpfReponsavel) {
        return repository.findByCpf(cpfReponsavel).orElseThrow(()->new PersonaNotFoundException());
    }

    @Override
    public UserDTO save(UserDTO dto) {
        if(this.personaExists(dto.getLogin())){
            throw new PersonaAlreadyExistsException("Este usuário já existe!");
        }

        Persona usuario = toPersona(dto);
        usuario = this.repository.save(usuario);
        return toUserDTO(usuario);
    }

    @Override
    public UserDTO toUserDTO(Persona user) {
        return UserDTO.builder()
                .login(user.getCpf())
                .name(user.getNome())
                .contato(user.getTelefone())
                .password(user.getSenha())
                .role(user.getPermissao().toString())
                .municipio(user.getCidade())
                .build();
    }

    @Override
    public Persona toPersona(UserDTO dto) {

        return Persona.builder()
                .cpf(dto.getLogin())
                .nome(dto.getName())
                .cidade(dto.getMunicipio())
                .telefone(dto.getContato())
                .permissao(EnumPermissao.valueOf(dto.getRole()))
                .senha(dto.getPassword())
                .build();
    }

    @Override
    public UserDTO getDetailsUser(String login) {
        Persona usuario = this.findByCpf(login);
        return toUserDTO(usuario);
    }
}
