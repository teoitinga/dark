package com.jp.dark.services.impls;

import com.jp.dark.config.Config;
import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.models.repository.ServiceProvidedRepository;
import com.jp.dark.services.CallService;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.ServiceProvidedService;
import com.jp.dark.services.VisitaService;
import com.jp.dark.utils.FolderGenerate;
import com.jp.dark.utils.Generates;
import com.jp.dark.utils.GeraCpfCnpj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VisitaServiceImpl implements VisitaService {

    private final VisitaRepository repository;

    private CallService callService;

    private PersonaService personaService;

    private ServiceProvidedService serviceProvidedService;

    private Config config;

    public VisitaServiceImpl(VisitaRepository visitaRepository,
                             CallRepository callRepository,
                             PersonaRepository personaRepository,
                             ServiceProvidedRepository serviceProvidedRepository,
                             Config config,
                             PasswordEncoder passwordEncoder
                             ) {

        this.repository = visitaRepository;
        this.callService = new CallServiceImpl(callRepository, config, personaRepository, serviceProvidedRepository, repository, passwordEncoder);
        this.personaService = new PersonaServiceImpl(personaRepository, passwordEncoder);
        this.serviceProvidedService = new ServiceProvidedServiceImpl(serviceProvidedRepository);
        this.config = config;
    }

    @Override
    @Transactional
    public VisitaDTO save(VisitaDTO visitaDto) {
        Visita visitaToSave = new Visita();

        /*
        0- Verifica se existe uma visita com o ID informada
         */
        String idInformado;
        try{
            idInformado = visitaDto.getCodigoVisita();
            Visita vs = this.findByCodigo(idInformado);
        }catch (NullPointerException ex){

        }catch (NoSuchElementException ex){

        }

        /*
        1- Verifica consistencia das informações dos produtores
         */
        List<Persona> produtores = verifyProdutores(visitaDto.getProdutores());

        //Configura produtores
        visitaToSave.setProdutores(produtores);

        /*
        2 - Atribui o código da visita
         */
        String cpfProdutor = produtores.get(0).getCpf();

        LocalDate dataVisita = LocalDate.parse(visitaDto.getDataDaVisita(), config.formater());

        LocalDateTime dataDoAtendimento = dataVisita.atTime(
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond()
                );

        String codigoDaVisita = Generates.keyCodeWithDate(cpfProdutor, dataDoAtendimento);

        visitaToSave.setCodigo(codigoDaVisita);


        /*
        Retorna o valor para o usuario
         */

        /*
        4 - Configurando o local da visita
         */
        String localDoAtendimeno = visitaDto.getLocalDoAtendimeno();

        visitaToSave.setLocalDoAtendimento(localDoAtendimeno);

        /*
        5- Configurando a situação atual
         */
        String situacaoAtual = visitaDto.getSituacaoAtual();
        visitaToSave.setSituacao(situacaoAtual);

        /*
        6 - Configurando a orientação técnica
         */
        String orientacao = visitaDto.getOrientacao();
        visitaToSave.setOrientacao(orientacao);

        /*
        7 - Configurando a recomendação
         */
        String recomendacao = visitaDto.getRecomendacao();
        visitaToSave.setRecomendacao(recomendacao);
        /*
        8 - Configurando a Data da visita
         */
        String dataDaVisita = visitaDto.getDataDaVisita();

        visitaToSave.setDataDaVisita(LocalDate.parse(dataDaVisita, config.formater()));
        visitaToSave.setRecomendacao(recomendacao);

        /*
        9- Configurando o municipio da visita
         */
        String municipio = visitaDto.getMunicipio();// this.personaService.getMunicpioDoUsuario();
        visitaToSave.setMunicipio(municipio);
        //Salva o registro da visita
        Visita visitaSaved = this.repository.save(visitaToSave);

        ////////////////////////////////////////////////////////////////
        /*
        * Bloco que trata os dados das chamadas
        3 - Verifica consistencia das informações das chamadas
        */

        List<Call> calls = verifyCalls(visitaDto.getChamadas(), visitaSaved);
        calls = this.callService.save(calls);
        visitaSaved.setChamadas(calls);
        //atualizando Visita
        visitaSaved = this.repository.save(visitaSaved);

        /*
        Fim do bloco que trata as chamdas
         */

        /*
        ############# Cria a pasta de atendimento
         */
        if(visitaDto.isCreateFolder()){

        StringBuilder idFolder = new StringBuilder();
        idFolder.append(visitaSaved.getProdutores().get(0).getCpf());
        idFolder.append(" -");
        idFolder.append(visitaSaved.getProdutores().get(0).getNome());

        LocalDateTime data = visitaSaved.getDataDaVisita().atTime(LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond());

        String folderName = Generates.keyCodeWithDate(idFolder.toString(), data);

        FolderGenerate.createFolder(folderName);

        }

        return toVisitaDTO(visitaSaved);
    }

    @Override
    public Visita findByCodigo(String idInformado) {
        Optional<Visita> response = this.repository.findByCodigo(idInformado);
        if(response.isPresent()){
        return response.get();

        }
        return null;
    }

    @Override
    public VisitaDTO getByCodigo(String codigo) {
        Visita vs = this.repository.findByCodigo(codigo).orElseThrow(()->new VisitaNotFoundException());
        return toVisitaDTO(vs);
    }
    @Override
    public List<Call> verifyCalls(List<CallDTOPost> chamadas, Visita visitaSaved) {
        /*
        Verifica se existe o serviço referente às chamadas
         */
        Visita vs = visitaSaved;

        List<Call> response = chamadas.stream()
                .map(call->this.callService.toCall(call, vs))
                .collect(Collectors.toList());

        return response;
    }

    @Override
    public List<Persona> verifyProdutores(List<ProdutorMinDTO> produtores) {
        /*
        1- Verifica informações dos produtores da lista
         */
        List<Persona> pessoal = produtores.stream()
                .filter(prd->this.cpfIsValid(prd.getCpf()))
                .map(prd->check(prd))
                .collect(Collectors.toList())
                ;
                //.map(prd->check(prd))
        return pessoal;
    }

    @Override
    public Persona check(ProdutorMinDTO prd) {
            if(this.personaExists(prd.getCpf())){
                //Se existe no banco de dados, somente retorna as informações deste produtor
                return this.personaService.findByCpf(prd.getCpf());
            }else{
                //Se não existe, é feito o registro deste produtor no banco de dados
                return this.personaService.save(prd);
            }

    }
    @Override
    public boolean personaExists(String cpf) {
        return this.personaService.personaExists(cpf);
    }

    @Override
    public boolean cpfIsValid(String cpf) {
        return new GeraCpfCnpj().isCPF(cpf);
    }

    @Override
    public VisitaDTO toVisitaDTO(Visita vs) {
        String dataDaVisita;

        try{
        dataDaVisita = vs.getDataDaVisita().toString();
        }catch (NullPointerException ex){
            dataDaVisita = null;
        }

        List<CallDTOPost> chamadas;
        try{
            chamadas = this.callService.toCallDTOPost(vs.getChamadas());
        }catch (NullPointerException ex){
            chamadas = null;
        }
        return VisitaDTO.builder()
                .codigoVisita(vs.getCodigo())
                .situacaoAtual(vs.getSituacao())
                .localDoAtendimeno(vs.getLocalDoAtendimento())
                .municipio(vs.getMunicipio())
                .recomendacao(vs.getRecomendacao())
                .orientacao(vs.getOrientacao())
                .produtores(this.personaService.toListProdutorMinDTO(vs.getProdutores()))
                .dataDaVisita(dataDaVisita)
                .chamadas(chamadas)
                .build();
    }

    @Override
    public Page<VisitaDTO> find(VisitaDTO dto, Pageable pageRequest) {

        Visita visita = toVisita(dto);

        Page<Visita> result = repository.findAll(pageRequest);

        List<VisitaDTO> list = result.getContent().stream()
                .map(entity->toVisitaDTO(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageRequest, result.getTotalElements());

    }

    private Visita toVisita(VisitaDTO dto) {
        List<Call> chamadas = null;
        String codigoVisita = null;
        String situacaoAtual = null;
        String recomendacao = null;
        LocalDate dataDaVisita = null;
        String localDoAtendimeno = null;
        String orientacao = null;
        List<Persona> produtores = null;

        try{
            chamadas = this.callService.toCall(dto.getChamadas());
            codigoVisita = dto.getCodigoVisita();
            situacaoAtual = dto.getSituacaoAtual();
            recomendacao = dto.getRecomendacao();
            dataDaVisita = LocalDate.parse(dto.getDataDaVisita(), config.formater());
            localDoAtendimeno = dto.getLocalDoAtendimeno();
            orientacao = dto.getOrientacao();
            produtores = this.personaService.toPersona(dto.getProdutores());
        }catch (NullPointerException ex){

        }


        return Visita.builder()
                .codigo(codigoVisita)
                .situacao(situacaoAtual)
                .recomendacao(recomendacao)
                .dataDaVisita(dataDaVisita)
                .localDoAtendimento(localDoAtendimeno)
                .orientacao(orientacao)
                .produtores(produtores)
                .build();
    }

    @Override
    public void delete(Visita visita) {
        Visita vs = this.repository.findByCodigo(visita.getCodigo()).orElseThrow(()->new VisitaNotFoundException());
        this.repository.delete(vs);
    }

    @Override
    public Visita save(Visita visita) {
        return this.repository.save(visita);
    }
}
