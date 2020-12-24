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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfoRendaServiceImpl implements com.jp.dark.services.InfoRendaService {

    private static final String CALL_OCURRENCY = "***";
    private static final String DESCRIPTION_SERVICE = "Levantamento de informações sociais e renda para emissão de documentos";

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
        callDAPLEV.setResponsavel(responsavel);
        callDAPLEV.setOcorrencia(CALL_OCURRENCY);
        callDAPLEV.setServico(DESCRIPTION_SERVICE);
        callDAPLEV.setValor(dto.getValorCobrado());
        callDAPLEV.setStatus(EnumStatus.INICIADA);
        LocalDate previsaoConclusao = visita.getDataDaVisita().plusDays(callDAPLEV.getServiceProvided().getTimeRemaining());

        callDAPLEV.setPrevisaoDeConclusao(previsaoConclusao);
        callDAPLEV.setValor(callDAPLEV.getServiceProvided().getDefaultValue());
        calls.add(callDAPLEV);

        //cRIANDO SEGUNDA CHAMADA DE SERVIÇO
        Call callDAP = new Call();
        try {
            callDAP = callDAPLEV.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        callDAP.setServiceProvided(this.serviceProvidedService.findByCodigoService("DAP"));
        previsaoConclusao = visita.getDataDaVisita().plusDays(callDAP.getServiceProvided().getTimeRemaining());

        callDAPLEV.setPrevisaoDeConclusao(previsaoConclusao);
        callDAPLEV.setValor(callDAP.getServiceProvided().getDefaultValue());
        calls.add(callDAP);
        //Salvando as chamdas no banco de dados
        calls = this.callService.save(calls);

        //Atualiza a visita
        visita.setChamadas(calls);
        visita = this.visitaService.save(visita);


        InfoRenda renda = new InfoRenda();
        List<ProducaoDTO> producao = dto.getProducao();

        Visita finalVisita = visita;
        List<InfoRenda> objects = producao.stream().map(entity -> toInfoRenda(entity, finalVisita)).collect(Collectors.toList());

        return toInfoRendaDTO(objects);
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

    private InfoRendaDTO toInfoRendaDTO(List<InfoRenda> objects) {
        InfoRendaDTO inf = new InfoRendaDTO();
        InfoRenda renda = objects.get(0);
        inf.setCodigoVisita(renda.getVisita().getCodigo());
        inf.setDataDaVisita(renda.getDataProducao().toString());
        inf.setLocalDoAtendimeno(renda.getVisita().getLocalDoAtendimento());
        inf.setOrientacao(renda.getVisita().getOrientacao());
        inf.setSituacaoAtual(renda.getVisita().getSituacao());
        inf.setRecomendacao(renda.getVisita().getRecomendacao());
        inf.setProdutores(toListPersonaMinDTO(renda.getVisita().getProdutores()));

        return inf;
    }

    private List<ProdutorMinDTO> toListPersonaMinDTO(List<Persona> produtores) {
        return this.personaService.toProdutorMinDTO(produtores);
    }


    private InfoRenda toInfoRenda(ProducaoDTO producao, Visita vs) {
        InfoRenda inf = new InfoRenda();
        ItemProducao item = this.itemService.findByCodigo(producao.getCodItemProducao());
        inf.setItemProducao(item);

        LocalDate dataProducao = LocalDate.parse(producao.getDataProducao(), config.formater());
        inf.setDataProducao(dataProducao);
        inf.setQuantidade(producao.getQuantidade());
        inf.setValorUnitario(producao.getValorUnitario());
        inf.setVisita(vs);

        inf = this.infoRendaRepository.save(inf);
        return inf;
    }

    @Override
    public ProducaoDTO toProducaoDTO(InfoRenda item) {
        InfoRenda inf = new InfoRenda();
        inf.setItemProducao(item.getItemProducao());
        return null;
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
