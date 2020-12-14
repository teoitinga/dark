package com.jp.dark.services.impls;

import com.jp.dark.config.Config;
import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.enums.EnumStatus;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.models.repository.ServiceProvidedRepository;
import com.jp.dark.services.CallService;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.ServiceProvidedService;
import com.jp.dark.utils.Generates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CallServiceImpl implements CallService {

    private final CallRepository repository;
    private ServiceProvidedService serviceProvidedService;
    private PersonaService personaService;
    private Config config;
    private VisitaRepository visitaRepository;
    private PasswordEncoder passwordEncoder;

    public CallServiceImpl(CallRepository callRepository,
                           Config config,
                           PersonaRepository personaRepository,
                           ServiceProvidedRepository serviceRepository,
                           VisitaRepository visitaRepository,
                           PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
        this.repository = callRepository;
        this.serviceProvidedService = new ServiceProvidedServiceImpl(serviceRepository);
        this.personaService = new PersonaServiceImpl(personaRepository, passwordEncoder);
        this.visitaRepository = visitaRepository;
        this.config = config;
    }

    @Override
    public List<CallDTOPost> toCallDTOPost(List<Call> chamadas) {
        return chamadas.stream().map(c ->toCallDTOPost(c)).collect(Collectors.toList());
    }

    @Override
    public CallDTOPost toCallDTOPost(Call c) {
        String servicoQuitadoEm = null;
        try{
            servicoQuitadoEm = c.getServicoQuitadoEm().toString();
        }catch (NullPointerException ex){

        }
        String cpf = null;
        try{
        cpf = c.getResponsavel().getCpf();

        }catch (NullPointerException ex){

        }

        Visita codigo = c.getVisita();

        String visitaCodigo;
        try{
        visitaCodigo = codigo.getCodigo();
        }catch (NullPointerException ex){
            visitaCodigo = null;
        }

        return CallDTOPost.builder()
                .valor(c.getValor())
                .servicoPrestado(c.getServico())
                .status(c.getStatus().toString())
                .serviceProvidedCode(c.getServiceProvided().getCodigo())
                .ocorrencia(c.getOcorrencia())
                .codigo(c.getCodigo())
                .CpfReponsavel(cpf)
                .servicoQuitadoEm(servicoQuitadoEm)
                .codigoDaVisita(visitaCodigo)
                .build();
    }

    @Override
    public CallDTO save(CallDTO dto, Visita vs) {
    if(dto.getCodigo() == null){
        dto.setCodigo(Generates.keyCode(Generates.createNumber()));
    }
        Call chamada = this.toCall(dto, vs);

        chamada = this.repository.save(chamada);
        log.info("Chamada saveed {}", chamada);
        return this.toCallDTO(chamada);
    }

    @Override
    public Call toCall(CallDTO dto, Visita codigoDaVisita) {
        ServiceProvided servico = this.serviceProvidedService.findByCodigoService(dto.getServiceProvidedCode());

        LocalDate previsaoDeConclusao = LocalDate.now().plusDays(servico.getTimeRemaining());

        LocalDate dataQuitado;
        try{
            dataQuitado = LocalDate.parse(dto.getServicoQuitadoEm(), config.formater());
        }catch (NullPointerException ex){
            dataQuitado = null;
        }
        return Call.builder()
                .serviceProvided(this.serviceProvidedService.findByCodigoService(dto.getServiceProvidedCode()))
                .status(EnumStatus.valueOf(dto.getStatus()))
                .codigo(dto.getCodigo())
                .servico(dto.getServico())
                .responsavel(this.personaService.findByCpf(dto.getCpfReponsavel()))
                .ocorrencia(dto.getOcorrencia())
                .valor(dto.getValor())
                .previsaoDeConclusao(previsaoDeConclusao)
                .servicoQuitadoEm(dataQuitado)
                .visita(codigoDaVisita)
                .build();
    }

    @Override
    public CallDTO toCallDTO(Call call) {
        String servicoQuitadoEm;
        try{
            servicoQuitadoEm = call.getServicoQuitadoEm().toString();
        }catch (NullPointerException ex){
            servicoQuitadoEm = null;
        }

        return CallDTO.builder()
                .valor(call.getValor())
                .status(call.getStatus().toString())
                .codigo(call.getCodigo())
                .ocorrencia(call.getOcorrencia())
                .servico(call.getServico())
                .serviceProvidedCode(call.getServiceProvided().getCodigo())
                .conclusaoPrevista(call.getPrevisaoDeConclusao().toString())
                .servicoQuitadoEm(servicoQuitadoEm)
                .CpfReponsavel(call.getResponsavel().getCpf())
                .codigoDaVisita(call.getVisita().getCodigo())
                .build();
    }

    @Override
    public Call toCall(CallDTOPost dto) {

        if(dto.getCodigo() == null){
            dto.setCodigo(Generates.keyCodeWithDate(Generates.createNumber(), LocalDateTime.now()));
        }

        ServiceProvided servico = this.serviceProvidedService.findByCodigoService(dto.getServiceProvidedCode());

        LocalDate previsaoDeConclusao = LocalDate.now().plusDays(servico.getTimeRemaining());

        LocalDate dataQuitado = null;
        try{
        dataQuitado = LocalDate.
                parse(dto.getServicoQuitadoEm(), config.formater());
        }catch (NullPointerException ex){

        }

        EnumStatus status = EnumStatus.INICIADA;

        try{
        status = EnumStatus.valueOf(dto.getStatus());
        }catch (NullPointerException ex){

        }
        Visita codigo = this.visitaRepository.findByCodigo(dto.getCodigoDaVisita()).orElse(null);

        return Call.builder()
                .serviceProvided(this.serviceProvidedService.findByCodigoService(dto.getServiceProvidedCode()))
                .status(status)
                .codigo(dto.getCodigo())
                .servico(dto.getServicoPrestado())
                .ocorrencia(dto.getOcorrencia())
                .valor(dto.getValor())
                .previsaoDeConclusao(previsaoDeConclusao)
                .servicoQuitadoEm(dataQuitado)
                .responsavel(this.personaService.findByCpf(dto.getCpfReponsavel()))
                .visita(codigo)
                .build();
    }

    @Override
    public List<Call> toCall(List<CallDTOPost> chamadas) {
        return chamadas.stream().map(c ->toCall(c)).collect(Collectors.toList());
    }

    @Override
    public List<Call> save(List<Call> call) {
        return call.stream().map(c-> Save(c)).collect(Collectors.toList());
    }
    @Override
    public Call Save(Call call) {
        call.setCodigo(Generates.keyCodeWithDate(Generates.createNumber(), LocalDateTime.now()));
        return this.repository.save(call);
    }

    @Override
    public Call toCall(Call call, Visita vs) {
        Call response = call;
        response.setVisita(vs);
        return response;
    }

    @Override
    public CallDTOPost save(CallDTOPost dto) {
        Call call = this.toCall(dto);
        Call saved = this.repository.save(call);
        return this.toCallDTOPost(saved);
    }

}
