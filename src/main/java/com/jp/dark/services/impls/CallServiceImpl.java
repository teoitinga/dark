package com.jp.dark.services.impls;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.CallNotFoundException;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.enums.EnumStatus;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.repository.ServiceProvidedRepository;
import com.jp.dark.services.CallService;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.ServiceProvidedService;
import com.jp.dark.services.VisitaService;
import com.jp.dark.utils.FolderGenerate;
import com.jp.dark.utils.Generates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CallServiceImpl implements CallService {

    private CallRepository repository;

    private VisitaService visitaService;

    private PersonaService personaService;

    private ServiceProvidedService serviceProvided;

    public CallServiceImpl(
            CallRepository repository,
            VisitaRepository visitaRepository,
            PersonaRepository personaRepository,
            ServiceProvidedRepository serviceProvidedRepository
    ) {
        this.repository = repository;
        this.visitaService = new VisitaServiceImpl(visitaRepository);
        this.personaService = new PersonaServiceImpl(personaRepository);
        this.serviceProvided = new ServiceProvidedServiceImpl(serviceProvidedRepository);
    }

    @Override
    @Transactional
    public CallDTO save(CallDTO callDTO) {
        //Configura o código da chamada com base no primeiro cpf encontrado
        String primeiroCpf = callDTO.getProdutores().get(0).getCpf();
        String keyCode = Generates.keyCode(primeiroCpf);
        callDTO.setCodigo(keyCode);

        boolean isNewVisita = false;
        //converte de DTO para chamada
        //primeiro verifica se existe a visita informarda
        String codigo = callDTO.getVisita().getCodigo();
        Optional<VisitaDTO> vs = null;
        try{
            vs = visitaService.getByCodigo(codigo);
            isNewVisita = false;
        }catch (NullPointerException ex){
            vs = Optional.empty();
        }

        if(!vs.isPresent()){
            //caso não exista, é criada a visita com os dados informados.
            VisitaDTO visita = callDTO.getVisita();

            //informa como chave de código, o CPF do primeiro produtor registrado
            visita.setCodigo(callDTO.getProdutores().get(0).getCpf());

            VisitaDTO savedVisita = visitaService.save(visita);
            isNewVisita = true;

            callDTO.setVisita(savedVisita);

            //Configura a chamada como INICIADA
            callDTO.setStatus(EnumStatus.INICIADA.toString());
        }else{
            callDTO.setVisita(vs.get());
        }

        /*
        /*Trata as informações do serviço prestado
        /*
         */
        //ou então recupera as informações da visita registrada
        Call entity = toCall(callDTO);

        //trata as informações dos produtores
        List<ProdutorMinDTO> produtores = callDTO.getProdutores();
        produtores = checkProdutores(produtores);
        //converte Produtores validos para Persona
        List<Persona> pessoalAtendido = toPersona(produtores);

        entity.setProdutores(pessoalAtendido);
        entity.setCodigo(callDTO.getCodigo());
        //salva
        Call savedCall = repository.save(entity);

        //Verifica se é uma nova visita para a gravação da pasta de atendimento
        if(isNewVisita){

            /*
            Definindo o nome da pasta para criação
             */
            String folderName = entity.getVisita().getCodigo()
                    .concat(" - ")
                    .concat(savedCall.getProdutores().get(0).getNome()
                            .toUpperCase()
                    );

            //Define o nome da pasta com todos os caracteres maiusculos e sem caracteres especiais
            folderName = Normalizer.normalize(folderName, Normalizer.Form.NFKD)
                    .replaceAll("[^\\p{ASCII}]", "");

            try {
                FolderGenerate.createFolder(folderName);
            } catch ( Exception e) {
                e.printStackTrace();
            }

        }
        return toCallDto(savedCall);
    }
    @Override
    public List<Persona> toPersona(List<ProdutorMinDTO> produtores) {
        return personaService.toPersona(produtores);
    }
    @Override
    public List<ProdutorMinDTO> checkProdutores(List<ProdutorMinDTO> produtores) {
        //para cada produtor da lista...
        return produtores.stream().map(prd->check(prd)).collect(Collectors.toList());
    }
    @Override
    public ProdutorMinDTO check(ProdutorMinDTO produtor) {
        ///verifica se o cpf é valido
        if(personaService.cpfIsValid(produtor.getCpf())){
            //verifica se existe no banco de dados
            if(personaService.PersonaExists(produtor.getCpf())){
                //////se existir não faz nada, só mantem a instancia
                return produtor;
            }else {
                //////se não existir, será feito o registro
                ProdutorMinDTO saved = personaService.save(produtor);

                return saved;
            }
        }
        //retorna os dados validos e registrados
        return null;
    }

    @Override
    public CallDTO toCallDto(Call call) {

        String codigo = "";

        try{
            codigo = call.getCodigo();
        }catch (NullPointerException ex){
            codigo = null;
        }
        Visita visitaInfo;
        VisitaDTO visita = null;
        try{
            visitaInfo = call.getVisita();
            visita = visitaService.toVisitaDto(visitaInfo);
        }catch (NullPointerException ex){
            visitaInfo = null;
        }
        String servico;
        try{
            servico = call.getServico();
        }catch (NullPointerException ex){
            servico = null;
        }
        String ocorrencia;
        try{
            ocorrencia= call.getOcorrencia();
        }catch (NullPointerException ex){
            ocorrencia = null;
        }

        List<ProdutorMinDTO> produtores;
        try{
            produtores = this.personaService.toProdutorMinDTO(call.getProdutores());
        }catch (NullPointerException ex){
            produtores = null;
        }

        String statusCall;
        try{
        statusCall = call.getStatus().getDescription();
        }catch (NullPointerException ex){
            statusCall = null;
        }

        /*
        Configurando os dados do Servico prestado
         */
        ServiceProvided serviceProvided = call.getServiceProvided();
        ServiceProvidedDTO serviceProvidedDTO = this.serviceProvided.toServiceProvidedDTO(call.getServiceProvided());

        LocalDate forecast = call.getForecast();

        BigDecimal valorDoServico = call.getValue();

        return CallDTO.builder()
                .codigo(codigo)
                .visita(visita)
                .servico(servico)
                .ocorrencia(ocorrencia)
                .produtores(produtores)
                .status(statusCall)
                .serviceProvided(serviceProvidedDTO)
                .valor(valorDoServico)
                .forecast(forecast)
                .build();
    }

    @Override
    public Call toCall(CallDTO callDto) {

        //Verifica se existe a visita informada

        String codigo;
        try{
            codigo = callDto.getCodigo();
        }catch (NullPointerException ex){
            throw new CallNotFoundException("Call with null id");
        }

        Optional<VisitaDTO> visitaByCodigo = visitaService.getByCodigo(codigo);
        VisitaDTO vs = null;
        if(visitaByCodigo.isPresent()){
            vs = visitaByCodigo.get();
        }
        Visita visita = visitaService.toVisita(vs);

        List<ProdutorMinDTO> produtores = callDto.getProdutores();

        EnumStatus status;
        try{
        status = EnumStatus.valueOf(callDto.getStatus());
        }catch (NullPointerException ex){
            status = null;
        }

        List<Persona> produtoresAtendidos = this.personaService.toPersona(produtores);

        ServiceProvided serviceProvided = null;
        BigDecimal valor = null;
        String referencyServ = null;
        try{

        serviceProvided = toServiceProvided(callDto.getServiceProvided());
        valor = serviceProvided.getDefaultValue();
        referencyServ = serviceProvided.getReferency();

        }catch (NullPointerException ex){

        }

        //Configurando informação do Campo Servico
        if(callDto.getServico().equals("") || callDto.getServico() == null){
            referencyServ = serviceProvided.getReferency();
        }else{
            referencyServ = callDto.getServico();
        }
        //Configurando a informação do valor do serviço
        BigDecimal valorDoServico = valor;
        try{
            valorDoServico = callDto.getValor();
        }catch (Exception ex){

        }
        /*
        Configurando a previsão de entrega do serviço
         */
        int timeRemaining = 7;

        try{
            timeRemaining = serviceProvided.getTimeRemaining();
        }catch (Exception ex){

        }
        log.info("Service provided: {}", serviceProvided);
        log.info("Tempo pra conclusao: {}", timeRemaining);

        LocalDate forecast = LocalDate.now().plusDays(timeRemaining);
        log.info("Previsão de entrega: {}", forecast);

        return Call.builder()
                .codigo(callDto.getCodigo())
                .ocorrencia(callDto.getOcorrencia())
                .visita(visita)
                .servico(callDto.getServico())
                .produtores(produtoresAtendidos)
                .status(status)
                .value(valorDoServico)
                .serviceProvided(serviceProvided)
                .forecast(forecast)
                .build();
    }

    private ServiceProvided toServiceProvided(ServiceProvidedDTO serviceProvided) {
        log.info("Servico informado: {}", serviceProvided);
        return this.serviceProvided.toServiceProvided(serviceProvided);
    }
}


