package com.jp.dark.services.impls;

import com.jp.dark.dtos.*;
import com.jp.dark.exceptions.PasswordInvalidException;
import com.jp.dark.exceptions.PersonaAlreadyExistsException;
import com.jp.dark.exceptions.PersonaNotFoundException;
import com.jp.dark.exceptions.ProdutorNotFoundException;
import com.jp.dark.models.entities.Escritorio;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.PersonaService;
import com.jp.dark.utils.GeraCpfCnpj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository repository;
    private PasswordEncoder encoder;

    public PersonaServiceImpl(PersonaRepository personaRepository, PasswordEncoder passwordEncoder) {
        this.repository = personaRepository;
        this.encoder = passwordEncoder;
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
    public String getMunicpioDoUsuario() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Persona usr = (Persona) authentication.getPrincipal();

        String login =  usr.getCpf();

        Persona usuario = this.findByCpf(login);

        return usuario.getCidade();
    }
    @Override
    public Escritorio getEslocDoUsuario() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Persona usr = (Persona) authentication.getPrincipal();

        String login =  usr.getCpf();

        Persona usuario = this.findByCpf(login);

        return usuario.getEsloc();
    }
    @Override
    public String getCpfDoUsuario() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Persona usr = (Persona) authentication.getPrincipal();

        String login =  usr.getCpf();

        Persona usuario = this.findByCpf(login);

        return usuario.getCpf();
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

        produtor.setEnabled(true);

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
        return repository.findByCpf(cpfReponsavel).orElseThrow(()->new PersonaNotFoundException(cpfReponsavel));
    }

    @Override
    public UserDTO save(UserDTO dto) {
        if(this.personaExists(dto.getLogin())){
            throw new PersonaAlreadyExistsException("Este usuário " +  dto.getName()+ "já existe!");
        }

        //criptografa a senha para registro no banco de dados
        dto.setPassword(encoder.encode(dto.getPassword()));

        Persona usuario = toPersona(dto);
        usuario.setEnabled(true);
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

    @Override
    public List<ProdutorDTO> findProdutorByNameContaining(String name) {
        EnumPermissao permissao = EnumPermissao.CLIENTES;
        List<Persona> result = this.repository.findByNomeContainingIgnoreCaseAndPermissao(name, permissao);
        List<ProdutorDTO> list = result.stream().map(entity->toProdutorDTO(entity)).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<UserDTO> findUserByNameContaining(String name) {
        EnumPermissao permissao = EnumPermissao.CLIENTES;
        List<Persona> result = this.repository.findByNomeContainingIgnoreCaseAndPermissaoNot(name, permissao);
        List<UserDTO> list = result.stream().map(entity->toUserDTO(entity)).collect(Collectors.toList());
        return list;
    }

    @Override
    public ProdutorDTO findProdutorByCpf(String cpf) {
        Persona persona = new Persona();
        try{
            persona = this.repository.findByCpf(cpf).orElseThrow(() -> new ProdutorNotFoundException(cpf));
        }catch (ProdutorNotFoundException e){
            return null;
        }
        ProdutorDTO produtor = this.toProdutorDTO(persona);
        return produtor;
    }
    @Override
    public PersonaDTO update(String cpf, PersonaDTO dto) {

        Persona persona;

        persona = this.repository.findByCpf(cpf).orElseThrow(() -> new PersonaNotFoundException(cpf));

        //Configura os dados atuais
        persona = this.toPersona(dto);
        persona.setCpf(cpf);

        Persona saved = this.repository.save(persona);

        PersonaDTO response = this.toPersonaDTO(saved);

        return response;
    }
    @Override
    public Persona toPersona(PersonaDTO dto) {
        Persona response = Persona.builder()
                .cpf(dto.getCpf())
                .nome(dto.getNome())
                .senha(dto.getSenha())
                .cep(dto.getCep())
                .permissao(EnumPermissao.valueOf(dto.getPermissao()))
                .categoria(EnumCategoria.valueOf(dto.getCategoria().toString()))
                .cidade(dto.getCidade())
                .endereco(dto.getEndereco())
                .telefone(dto.getTelefone())
                .nascimento(dto.getNascimento())
                .build();
        return response;
    }
    @Override
    public PersonaDTO toPersonaDTO(Persona persona) {
        PersonaDTO response = PersonaDTO.builder()
                .cpf(persona.getCpf())
                .nome(persona.getNome())
                .senha(persona.getSenha())
                .cep(persona.getCep())
                .permissao(persona.getPermissao().toString())
                .categoria(persona.getCategoria().toString())
                .cidade(persona.getCidade())
                .endereco(persona.getEndereco())
                .telefone(persona.getTelefone())
                .nascimento(persona.getNascimento())
                .build();
        return response;
    }

    @Override
    public UserDTO update(String login, UserDTO dto) {

        //Verifica integridade da senha
        if(dto.getPassword().equals("")){
            throw new PasswordInvalidException("Senha com valor vazio não é permitido");
        }
        //criptografa a senha para registro no banco de dados
        dto.setPassword(encoder.encode(dto.getPassword()));

        Persona persona;
        persona = this.repository.findByCpf(login).orElseThrow(() -> new PersonaNotFoundException());

        //Converte LOGIN para CPF
        String cpf = login;

        persona = toPersona(dto);
        persona.setCpf(cpf);

        Persona saved = this.repository.save(persona);

        UserDTO response = this.toUserDTO(saved);

        return response;
    }

}
