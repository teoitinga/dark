package com.jp.dark.services.impls;

import com.jp.dark.config.Config;
import com.jp.dark.dtos.EslocDTO;
import com.jp.dark.exceptions.*;
import com.jp.dark.models.entities.Escritorio;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.models.repository.EscritorioRepository;
import com.jp.dark.services.EslocService;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.ServiceProvidedService;
import com.jp.dark.vos.UsuarioVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EslocServiceImpl implements EslocService {

    private EscritorioRepository repository;
    private ServiceProvidedService servicoProvidedService;
    private PersonaService personaService;
    private Config config;

    public EslocServiceImpl(
            EscritorioRepository repository,
            ServiceProvidedService servicoProvidedService,
            PersonaService personaService,
            Config config
    ) {
        this.repository = repository;
        this.servicoProvidedService = servicoProvidedService;
        this.personaService = personaService;
        this.config = config;

    }

    @Override
    public Escritorio findById(String codEsloc) {
        return this.repository.findById(Integer.parseInt(codEsloc))
                .orElseThrow(()-> new EslocNotFound("Escritório não localizado."));
    }

    @Override
    public EslocDTO save(EslocDTO dto) {
        Escritorio escritorio = toEscritorio(dto);
        EslocDTO esloc = toEscritorioDto(this.repository.save(escritorio));
        return esloc;
    }

    @Override
    public void addService(String codEsloc, String codService) {

        //verifica se existeo esloc
        Escritorio escritorio = this.findById(codEsloc);

        //verifica se existe o servico
        ServiceProvided servico = this.servicoProvidedService.findByCodigoService(codService);

        //Verifica se já existe este registro

        List<ServiceProvided> serviceProvideds = escritorio.getServicos();

        Optional<ServiceProvided> hasService = serviceProvideds.stream()
                .filter(srv -> servico.getCodigo().equals(srv.getCodigo()))
                .findAny();

        if(hasService.isPresent()){
            throw new ServiceOnEslocAlreadyException("Este serviço já está registrado.");
        }

        //registra
        escritorio.addService(servico);
        escritorio = this.repository.save(escritorio);

    }

    @Override
    public void addUser(String codEsloc, String cpf) {
        //verifica se existeo esloc
        Escritorio escritorio = this.findById(codEsloc);

        //verifica se existe o usuario
        Persona persona = this.personaService.findByCpf(cpf);

        //Verifica se já existe este registro

        List<Persona> personas = escritorio.getPublico();

        Optional<Persona> hasPersona = personas.stream()
                .filter(ps -> persona.getCpf().equals(ps.getCpf()))
                .findAny();

        if(hasPersona.isPresent()){
            throw new PersonaOnEslocAlreadyException("Este usuário já está registrado neste escritório.");
        }

        //registra
        escritorio.addPersona(persona);
        this.repository.save(escritorio);

    }

    @Override
    public void removeUser(String codEsloc, String cpf) {

        //verifica se existe o esloc
        Escritorio escritorio = this.findById(codEsloc);

        //verifica se existe o usuario
        log.info("Busca por usuario {}", cpf);
        Persona persona = this.personaService.findByCpf(cpf);
        log.info("usuario {}", persona);

        //Verifica se o escritório possui um registro deste usuário
        boolean exists = escritorio.getPublico().contains(persona);

        if(!exists){
            throw new PersonaNotFoundException(persona.getCpf());
        }

        //registra
        escritorio.removePersona(persona);

        this.repository.save(escritorio);
    }

    @Override
    public void removeService(String codEsloc, String codService) {

        //Verifica se existe o serviço para o escritorio

        //verifica se existe o esloc
        Escritorio escritorio = this.findById(codEsloc);

        //verifica se existe o servico
        ServiceProvided servico = this.servicoProvidedService.findByCodigoService(codService);

        int existe = escritorio.getServicos().indexOf(servico);

        boolean exists = escritorio.getServicos().contains(servico);

        if(!exists){
            throw new ServiceProvidedNotFoundException("Serviço não encontrado para este escritório!");
        }
        //Fim da verificação de serviço para o escritorio

        //registra
        escritorio.removeService(servico);

        this.repository.save(escritorio);

    }

    @Override
    public List<UsuarioVO> getUsersForEsloc(String codEsloc) {
        //verifica se existe o esloc
        Escritorio escritorio = this.findById(codEsloc);

        List<Persona> usuarios = escritorio.getPublico();


        List<UsuarioVO> response = usuarios.stream()
                .map(usuario->toUsuarioVO(usuario))
                .collect(Collectors.toList());

        return response;
    }

    @Override
    public List<EslocDTO> getAllEslocs() {
        List<Escritorio> eslocs = this.repository.findAll();
        List<EslocDTO> eslocsDto = eslocs.stream()
                .map(e->toEscritorioDto(e))
                .collect(Collectors.toList());

        return eslocsDto;
    }

    private UsuarioVO toUsuarioVO(Persona usuario) {
        return UsuarioVO.builder()
                .cpf(usuario.getCpf())
                .nome(usuario.getNome())
                .nascimento(usuario.getNascimento().format(this.config.formaterPatternddMMyyyy()))
                .endereco(usuario.getEndereco())
                .cidade(usuario.getCidade())
                .cep(usuario.getCep())
                .categoria(usuario.getCategoria().toString())
                .permissao(usuario.getPermissao().toString())
                .enabled(usuario.getEnabled())
                .build();
    }

    private EslocDTO toEscritorioDto(Escritorio esloc) {
        return EslocDTO.builder()
                .codigo(esloc.getCodigo())
                .descricao(esloc.getDescricao())
                .endereco(esloc.getEndereco())
                .referency(esloc.getReferency())
                .email(esloc.getEmail())
                .fone(esloc.getFone())
                .municipio(esloc.getMunicipio())
                .zap(esloc.getZap())
                .build();
    }

    private Escritorio toEscritorio(EslocDTO esloc) {
        return Escritorio.builder()
                .codigo(esloc.getCodigo())
                .descricao(esloc.getDescricao())
                .endereco(esloc.getEndereco())
                .referency(esloc.getReferency())
                .email(esloc.getEmail())
                .fone(esloc.getFone())
                .municipio(esloc.getMunicipio())
                .zap(esloc.getZap())
                .build();
    }
}
