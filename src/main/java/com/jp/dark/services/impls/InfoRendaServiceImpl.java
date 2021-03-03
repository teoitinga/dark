package com.jp.dark.services.impls;

import com.jp.dark.config.Config;
import com.jp.dark.dtos.*;
import com.jp.dark.models.entities.*;
import com.jp.dark.models.enums.EnumStatus;
import com.jp.dark.models.repository.InfoRendaRepository;
import com.jp.dark.models.repository.ServiceProvidedRepository;
import com.jp.dark.services.*;
import com.jp.dark.utils.Generates;
import com.jp.dark.utils.GeraCpfCnpj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InfoRendaServiceImpl implements InfoRendaService {

    private static final String CALL_OCURRENCY = "***";
    private static final String DESCRIPTION_SERVICE = "Levantamento de informações sociais e renda para emissão de laudo.";

    private Config config;
    private VisitaService visitaService;
    private ServiceProvidedService serviceProvidedService;
    private PersonaService personaService;
    private CallService callService;
    private ItemProducaoService itemService;
    private InfoRendaRepository infoRendaRepository;
    private OrigemRendaService origemRendaService;

    public InfoRendaServiceImpl(
            Config config,
            VisitaService visitaService,
            ServiceProvidedService serviceProvidedService,
            PersonaService personaService,
            CallService callService,
            ItemProducaoService itemService,
            InfoRendaRepository infoRendaRepository,
            OrigemRendaService origemRendaService
            ) {
        this.config = config;
        this.visitaService = visitaService;
        this.serviceProvidedService = serviceProvidedService;
        this.personaService = personaService;
        this.callService = callService;
        this.itemService = itemService;
        this.infoRendaRepository = infoRendaRepository;
        this.origemRendaService = origemRendaService;

    }

    @Override
    public InfoRendaDTO save(InfoRendaDTO dto) {
        /*
        1 - Verifica consistencia dos produtores informados
         */
        List<Persona> produtores = verifyProdutores(dto.getProdutores());
        //Define o titular da visita
        Persona titular = produtores.get(0);

        /*
        2 - Registra a visita
         */
        Visita visita = new Visita();
        LocalDate dataDaVisita = LocalDate.parse(dto.getDataDaVisita(), config.formater());

        int hora = LocalDateTime.now().getHour();
        int minuto = LocalDateTime.now().getMinute();

        visita.setCodigo(Generates.keyCodeWithDate(titular.getCpf(), dataDaVisita.atTime(hora, minuto)));
        visita.setRecomendacao(dto.getRecomendacao());
        visita.setOrientacao(dto.getOrientacao());
        visita.setDataDaVisita(dataDaVisita);
        visita.setSituacao(dto.getSituacaoAtual());
        visita.setLocalDoAtendimento(dto.getLocalDoAtendimeno());
        visita.setProdutores(produtores);
        //salva registro de visita
        visita = this.visitaService.save(visita);

        //Informa registro de chmadas referentes a este atendimento
        List<Call> calls = new ArrayList<>();

        //Criando a primeira chamada de serviço
        Call callDAPLEV = new Call();
        callDAPLEV.setVisita(visita);
        callDAPLEV.setServiceProvided(this.serviceProvidedService.findByCodigoService("DAPLEV"));
        //Buscando o usuario logado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;

        if (principal instanceof UserDetails) {
            nome = ((UserDetails)principal).getUsername();
        } else {
            nome = principal.toString();
        }

        Persona responsavel = this.personaService.findByCpf(nome);
        /*
        Registrando serviços relacionados
         */
        callDAPLEV.setResponsavel(responsavel);
        callDAPLEV.setOcorrencia(CALL_OCURRENCY);
        callDAPLEV.setServico(DESCRIPTION_SERVICE);
        callDAPLEV.setValor(dto.getValorCobrado());
        callDAPLEV.setStatus(EnumStatus.FINALIZADA);
        LocalDate previsaoConclusao = visita.getDataDaVisita().plusDays(callDAPLEV.getServiceProvided().getTimeRemaining());

        callDAPLEV.setPrevisaoDeConclusao(previsaoConclusao);
        callDAPLEV.setValor(dto.getValorCobrado());
        calls.add(callDAPLEV);

        /*
        //Criando Segunda Chamada de serviço
        Call callDAP = new Call();
        try {
            callDAP = callDAPLEV.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        ServiceProvided dap = this.serviceProvidedService.findByCodigoService("DAP");

        callDAP.setServiceProvided(dap);
        callDAP.setServico(dap.getReferency());
        callDAP.setServicoQuitadoEm(LocalDate.now());
        callDAP.setStatus(EnumStatus.INICIADA);
        previsaoConclusao = visita.getDataDaVisita().plusDays(callDAP.getServiceProvided().getTimeRemaining());

        callDAPLEV.setPrevisaoDeConclusao(previsaoConclusao);
        callDAPLEV.setValor(callDAP.getServiceProvided().getDefaultValue());
        calls.add(callDAP);
        */
        //Salvando as chamdas no banco de dados
        calls = this.callService.save(calls);
        /*
        Fim registro de serviços relacionados
         */
        //Atualiza a visita
        visita.setChamadas(calls);
        visita = this.visitaService.save(visita);

        InfoRenda renda = new InfoRenda();
        List<ProducaoDTO> producao = dto.getProducaoAnual();

        Visita visitaSaved = visita;
        renda = toInfoRenda(producao, visitaSaved, dto);

        return toInfoRendaDTO(renda);
    }

     private List<Persona> verifyProdutores(List<ProdutorMinDTO> produtores) {
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

    private Persona check(ProdutorMinDTO prd) {
        if(this.personaExists(prd.getCpf())){
            //Se existe no banco de dados, somente retorna as informações deste produtor
            return this.personaService.findByCpf(prd.getCpf());
        }else{
            //Se não existe, é feito o registro deste produtor no banco de dados
            return this.personaService.save(prd);
        }
    }

    private boolean personaExists(String cpf) {
        return this.personaService.personaExists(cpf);
    }

    private boolean cpfIsValid(String cpf) {
        return new GeraCpfCnpj().isCPF(cpf);
    }

    private InfoRendaDTO toInfoRendaDTO(InfoRenda rnd) {

        List<ProducaoDTO> producao = rnd.getProducaoAnual().stream().
                map(prd->toProducaoDTO(prd))
                .collect(Collectors.toList());

        List<ProdutorMinDTO> produtores = rnd.getVisita().getProdutores()
                .stream().map(prd->this.personaService.toProdutorMinDTO(prd))
                .collect(Collectors.toList());

        return InfoRendaDTO.builder()
                .codigoInfo(rnd.getCodigo().toString())
                .codigoVisita(rnd.getVisita().getCodigo().toString())
                .producaoAnual(producao)
                .dataDaVisita(rnd.getVisita().getDataDaVisita().format(config.formaterPatternddMMyyyy()))
                .localDoAtendimeno(rnd.getVisita().getLocalDoAtendimento())
                .orientacao(rnd.getVisita().getOrientacao())
                .recomendacao(rnd.getVisita().getRecomendacao())
                .situacaoAtual(rnd.getVisita().getSituacao())
                .produtores(produtores)
                .areaExplorada(rnd.getAreaExplorada())
                .areaImovelPrincipal(rnd.getAreaImovelPrincipal())
                .membrosDaFamilia(rnd.getMembrosDaFamilia())
                .quantidadePropriedades(rnd.getQuantidadePropriedades())
                .valorCobrado(rnd.getVisita().getChamadas().stream().map(ent->ent.getValor()).reduce(BigDecimal.valueOf(0.0),BigDecimal::add))
                .build();
    }

    private ProducaoDTO toProducaoDTO(Producao prd) {
        return ProducaoDTO.builder()
                .codItemProducao(prd.getItemProducao().getCodigo())
                .valorUnitario(prd.getValorUnitario())
                .quantidade(prd.getQuantidade())
                .dataProducao(prd.getDataDaProducao().format(this.config.formater()))
                .codigo(prd.getCodigo())
                .descricao(prd.getDescricao())
                .build();
    }

    private List<ProdutorMinDTO> toListPersonaMinDTO(List<Persona> produtores) {
        return this.personaService.toProdutorMinDTO(produtores);
    }


    private InfoRenda toInfoRenda(List<ProducaoDTO> producao, Visita vs, InfoRendaDTO dto) {

        List<Producao> producaoAnual = producao.stream().map(prd->toProducao(prd)).collect(Collectors.toList());

        InfoRenda infoRenda = InfoRenda.builder()
                .visita(vs)
                .areaExplorada(dto.getAreaExplorada())
                .areaImovelPrincipal(dto.getAreaImovelPrincipal())
                .membrosDaFamilia(dto.getMembrosDaFamilia())
                .quantidadePropriedades(dto.getQuantidadePropriedades())
                .producaoAnual(producaoAnual)
                .build();

        infoRenda = this.infoRendaRepository.save(infoRenda);
        return infoRenda;
    }

    public Producao toProducao(ProducaoDTO prd) {

        ItemProducao item = this.itemService.findByCodigo(prd.getCodItemProducao());

        Integer codigo;
        try{
            codigo = prd.getCodigo();
        }catch (NullPointerException exc){
            codigo = null;
        }
        return Producao.builder()
                .codigo(codigo)
                .itemProducao(item)
                .descricao(prd.getDescricao())
                .quantidade(prd.getQuantidade())
                .valorUnitario(prd.getValorUnitario())
                .dataDaProducao(LocalDate.parse(prd.getDataProducao(), this.config.formater()))
                .build();
    }

    @Override
    public InfoRenda register(ProducaoDTO prd, Visita vs) {
        return null;
    }

    @Override
    public List<ItemProducaoDTO> findByDescricaoContaining(String descricao) {
        return itemService.findByDescricaoContainingIgnoreCase(descricao);
    }

    @Override
    public List<OrigemRendaDTO> findOrigemByDescricaoContaining(String org) {
        return this.origemRendaService.findByDescricaoContaining(org);

    }
}
