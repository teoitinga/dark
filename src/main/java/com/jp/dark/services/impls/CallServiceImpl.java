package com.jp.dark.services.impls;

import com.jp.dark.config.Config;
import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.dtos.CallDTOView;
import com.jp.dark.exceptions.CallNotFoundException;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.models.entities.*;
import com.jp.dark.models.enums.EnumStatus;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.models.repository.ServiceProvidedRepository;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.services.CallService;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.ServiceProvidedService;
import com.jp.dark.utils.Generates;
import com.jp.dark.vos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                           PasswordEncoder passwordEncoder
    ) {

        this.passwordEncoder = passwordEncoder;
        this.repository = callRepository;
        this.serviceProvidedService = new ServiceProvidedServiceImpl(serviceRepository);
        this.personaService = new PersonaServiceImpl(personaRepository, passwordEncoder);
        this.visitaRepository = visitaRepository;
        this.config = config;
    }

    @Override
    public List<CallDTOPost> toCallDTOPost(List<Call> chamadas) {
        return chamadas.stream().map(c -> toCallDTOPost(c)).collect(Collectors.toList());
    }

    @Override
    public CallDTOPost toCallDTOPost(Call c) {
        String servicoQuitadoEm = null;
        try {
            servicoQuitadoEm = c.getServicoQuitadoEm().toString();
        } catch (NullPointerException ex) {

        }
        String cpf = null;
        try {
            cpf = c.getResponsavel().getCpf();

        } catch (NullPointerException ex) {

        }

        Visita codigo = c.getVisita();

        String visitaCodigo;
        try {
            visitaCodigo = codigo.getCodigo();
        } catch (NullPointerException ex) {
            visitaCodigo = null;
        }

        String status;
        try {
            status = c.getStatus().toString();
        } catch (NullPointerException e) {
            status = EnumStatus.INICIADA.toString();
        }
        return CallDTOPost.builder()
                .valor(c.getValor())
                .servicoPrestado(c.getServico())
                .status(status)
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
        if (dto.getCodigo() == null) {
            dto.setCodigo(Generates.keyCode(Generates.createNumber()));
        }
        Call chamada = this.toCall(dto, vs);

        chamada = this.repository.save(chamada);
        return this.toCallDTO(chamada);
    }

    @Override
    public Call toCall(CallDTO dto, Visita codigoDaVisita) {
        ServiceProvided servico = this.serviceProvidedService.findByCodigoService(dto.getServiceProvidedCode());

        LocalDate previsaoDeConclusao = LocalDate.now().plusDays(servico.getTimeRemaining());

        LocalDate dataQuitado;
        try {
            dataQuitado = LocalDate.parse(dto.getServicoQuitadoEm(), config.formater());
        } catch (NullPointerException ex) {
            dataQuitado = null;
        }
        EnumStatus status;
        try {
            status = EnumStatus.valueOf(dto.getStatus());
        } catch (NullPointerException e) {
            status = EnumStatus.INICIADA;
        }
        return Call.builder()
                .serviceProvided(this.serviceProvidedService.findByCodigoService(dto.getServiceProvidedCode()))
                .status(status)
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
        try {
            servicoQuitadoEm = call.getServicoQuitadoEm().toString();
        } catch (NullPointerException ex) {
            servicoQuitadoEm = null;
        }

        String status;
        try {
            status = call.getStatus().toString();
        } catch (NullPointerException e) {
            status = EnumStatus.INICIADA.toString();
        }
        return CallDTO.builder()
                .valor(call.getValor())
                .status(status)
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

        if (dto.getCodigo() == null) {
            dto.setCodigo(Generates.keyCodeWithDate(Generates.createNumber(), LocalDateTime.now()));
        }

        ServiceProvided servico = this.serviceProvidedService.findByCodigoService(dto.getServiceProvidedCode());

        LocalDate previsaoDeConclusao = LocalDate.now().plusDays(servico.getTimeRemaining());

        LocalDate dataQuitado;
        try {
            dataQuitado = LocalDate.
                    parse(dto.getServicoQuitadoEm(), config.formater());
        } catch (NullPointerException ex) {
            dataQuitado = null;
        }

        EnumStatus status;

        try {
            status = EnumStatus.valueOf(dto.getStatus());
        } catch (NullPointerException ex) {
            status = EnumStatus.INICIADA;
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
        return chamadas.stream().map(c -> toCall(c)).collect(Collectors.toList());
    }

    @Override
    public List<Call> save(List<Call> call) {
        return call.stream().map(c -> save(c)).collect(Collectors.toList());
    }

    @Override
    public Call save(Call call) {
        log.info("Salvando registro de chamdas: {}", call);
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

    @Override
    public CallDTOPost update(CallDTOPost dto, String id) {
        Call call = this.repository.findById(id).orElseThrow(() -> new CallNotFoundException("Chamada de serviço não encontrada"));
        ServiceProvided serviceProvided = this.serviceProvidedService.findByCodigoService(dto.getServiceProvidedCode());
        call.setServiceProvided(serviceProvided);
        Visita visita = this.visitaRepository.findByCodigo(dto.getCodigoDaVisita()).orElseThrow(() -> new VisitaNotFoundException());
        call.setVisita(visita);
        call.setOcorrencia(dto.getOcorrencia());
        Persona reponsavel = this.personaService.findByCpf(dto.getCpfReponsavel());
        call.setResponsavel(reponsavel);
        call.setServico(dto.getServicoPrestado());
        call.setStatus(EnumStatus.valueOf(dto.getStatus()));
        call.setValor(dto.getValor());
        call = this.repository.save(call);
        return this.toCallDTOPost(call);
    }

    @Override
    public CallDTOPost cancel(String id) {
        Call call = this.repository.findById(id).orElseThrow(() -> new CallNotFoundException("Chamada de serviço não encontrada"));
        call.setStatus(EnumStatus.CANCELADA);
        call = this.repository.save(call);
        return this.toCallDTOPost(call);
    }

    @Override
    public CallDTOPost finalize(String id) {
        Call call = this.repository.findById(id).orElseThrow(() -> new CallNotFoundException("Chamada de serviço não encontrada"));
        call.setStatus(EnumStatus.FINALIZADA);
        call = this.repository.save(call);
        return this.toCallDTOPost(call);
    }

    @Override
    public CallDTOPost initialize(String id) {
        Call call = this.repository.findById(id).orElseThrow(() -> new CallNotFoundException("Chamada de serviço não encontrada"));
        call.setStatus(EnumStatus.INICIADA);
        call = this.repository.save(call);
        return this.toCallDTOPost(call);
    }

    @Override
    public CallDTOPost updateValue(String id, BigDecimal valur) {
        Call call = this.repository.findById(id).orElseThrow(() -> new CallNotFoundException("Chamada de serviço não encontrada"));
        call.setStatus(EnumStatus.INICIADA);
        call = this.repository.save(call);
        return this.toCallDTOPost(call);
    }

    @Override
    public Page<CallDTOPost> getCalls(Pageable pageRequest) {

        //Buscando o usuario logado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;

        if (principal instanceof UserDetails) {
            nome = ((UserDetails) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        Persona responsavel = this.personaService.findByCpf(nome);

        Page<Call> result = this.repository.findByResponsavel(responsavel, pageRequest);

        List<CallDTOPost> list = result.getContent().stream()
                .map(entity -> toCallDTOPost(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageRequest, result.getTotalElements());
    }

    @Override
    public Call toCall(CallDTOPost call, Visita vs) {
        Call c = new Call();
        c.setVisita(vs);
        c.setOcorrencia(call.getOcorrencia());
        c.setResponsavel(personaService.findByCpf(call.getCpfReponsavel()));

        ServiceProvided servico = this.serviceProvidedService.findByCodigoService(call.getServiceProvidedCode());
        c.setServiceProvided(servico);

        c.setServico(call.getServicoPrestado());
        c.setValor(call.getValor());

        LocalDate previsao = vs.getDataDaVisita().plusDays(servico.getTimeRemaining());
        c.setPrevisaoDeConclusao(previsao);

        EnumStatus status;
        try {
            status = EnumStatus.valueOf(call.getStatus());
        } catch (NullPointerException ex) {
            status = EnumStatus.INICIADA;
        }
        c.setStatus(status);

        LocalDate quitadoEm;
        try {
            quitadoEm = LocalDate.parse(call.getServicoQuitadoEm(), config.formater());
            c.setServicoQuitadoEm(quitadoEm);
        } catch (NullPointerException exc) {
            c.setServicoQuitadoEm(null);
        }

        return c;
    }

    @Override
    public Page<CallDTOView> getCallsView(Pageable pageRequest) {
        //Buscando o usuario logado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;

        if (principal instanceof UserDetails) {
            nome = ((UserDetails) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        Persona responsavel = this.personaService.findByCpf(nome);

        Page<Call> result = this.repository.findCallsEmAbertoPorResponsavel(responsavel, pageRequest);

        List<CallDTOView> list = result.getContent().stream()
                .map(entity -> toCallDTOPostView(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageRequest, result.getTotalElements());
    }

    @Override
    public Integer getCallsOperation() {

        //Buscando o usuario logado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;

        if (principal instanceof UserDetails) {
            nome = ((UserDetails) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        Persona responsavel = this.personaService.findByCpf(nome);

        Integer result = this.repository.countCalls(responsavel);

        if (result == null) {
            return 0;
        }

        return result;
    }

    @Override
    public Page<Call> findAllCalls(Pageable pageRequest) {
        return repository.findAllCalls(pageRequest);
    }

    @Override
    public List<Call> findAllCallsUser(Pageable pageRequest) {
        //Buscando o usuario logado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;

        if (principal instanceof UserDetails) {
            nome = ((UserDetails) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        Persona responsavel = this.personaService.findByCpf(nome);

        List<Call> result = this.repository.findAllCallsUser(responsavel, pageRequest);

        return result;
    }

    @Override
    public Call toCall(CallVO vo, Visita visita) {
        ServiceProvided servico = this.serviceProvidedService.findByCodigoService(vo.getServiceProvidedCode());

        LocalDate previsaoDeConclusao = LocalDate.now().plusDays(servico.getTimeRemaining());

        LocalDate dataQuitado;
        dataQuitado = null;

        EnumStatus status;
        try {
            status = EnumStatus.valueOf(EnumStatus.INICIADA.toString());
        } catch (NullPointerException e) {
            status = EnumStatus.INICIADA;
        }
        return Call.builder()
                .serviceProvided(servico)
                .status(status)
                .servico(vo.getServicoPrestado())
                .responsavel(this.personaService.findByCpf(vo.getCpfReponsavel()))
                .ocorrencia(vo.getOcorrencia())
                .valor(vo.getValor())
                .previsaoDeConclusao(previsaoDeConclusao)
                .servicoQuitadoEm(dataQuitado)
                .visita(visita)
                .build();
    }

    @Override
    public CallVO toCallVO(Call call) {
        return new CallVO(
                call.getServico(),
                call.getServiceProvided().getCodigo(),
                call.getOcorrencia(),
                call.getResponsavel().getCpf(),
                call.getValor(),
                call.getStatus().toString()
        );
    }


    @Override
    public Page<ServicosPrestadosVO> getServicos(Pageable pageRequest) {

        LocalDateTime inicio = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fim = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        Page<Object[]> result = this.repository.findCallsPorPeriodo(inicio, fim, pageRequest);
        List<ServicosPrestadosVO> list = result.getContent().stream().map(data -> mapServicosPrestadosVO(data)).collect(Collectors.toList());

        return new PageImpl<>(list, pageRequest, result.getTotalElements());
    }

    @Override
    public ServicosReportVO getServicosReportMensal(int codEsloc, String mes) {

        LocalDateTime inicio = LocalDateTime.now().withDayOfMonth(1);

        LocalDateTime fim = LocalDateTime.now();

        String header_report = "Relatório mensal de serviços prestados";

        int mesNumber = 0;

        try {
            mesNumber = Integer.parseInt(mes);
        } catch (Exception e) {
            mesNumber = 0;
        }

        if (mesNumber > 0 && mesNumber <= 12) {

            inicio = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            inicio = inicio.withMonth(mesNumber);

            fim = LocalDateTime.now().withMonth(mesNumber).withHour(23).withMinute(59).withSecond(59);
        }

        //buscando os dados no repositório
        List<Object[]> result = this.repository.findCallsReportPorPeriodo(inicio, fim, codEsloc);

        List<ServicoDetalheVO> list = result.stream().map(data -> mapServicoDetalheVO(data)).collect(Collectors.toList());

        ServicosReportVO servicoReport = ServicosReportVO.builder()
                .dataFinal(fim.format(config.formaterPatternddMMyyyy()))
                .dataInicial(inicio.format(config.formaterPatternddMMyyyy()))
                .build();

        servicoReport.setRelatorio(header_report);
        servicoReport.setServico(list);

        return servicoReport;
    }
    @Override
    public ServicosReportVO getServicosReportAnual(int codEsloc) {

        LocalDateTime inicio = LocalDateTime.now().withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

        LocalDateTime fim = LocalDateTime.now().withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59);

        String header_report = "Relatório anual de serviços prestados";

         //buscando os dados no repositório
        List<Object[]> result = this.repository.findCallsReportPorPeriodo(inicio, fim, codEsloc);

        List<ServicoDetalheVO> list = result.stream().map(data -> mapServicoDetalheVO(data)).collect(Collectors.toList());

        ServicosReportVO servicoReport = ServicosReportVO.builder()
                .dataFinal(fim.format(config.formaterPatternddMMyyyy()))
                .dataInicial(inicio.format(config.formaterPatternddMMyyyy()))
                .build();

        servicoReport.setRelatorio(header_report);
        servicoReport.setServico(list);

        return servicoReport;
    }

    private ServicoDetalheVO mapServicoDetalheVO(Object[] data) {
        return ServicoDetalheVO.builder()
                .servicoPrestado(data[0].toString())
                .dataServico(data[1].toString())
                .esloc(data[2].toString())
                .municipio(data[3].toString())
                .codChamada(data[4].toString())
                .nomeBeneficiario(data[5].toString())
                .nomeResponsavel(data[6].toString())
                .codEsloc(data[7].toString())
                .build();
    }

    @Override
    public List<AtividadesPrestadasVO> getAtividades(String dataInicial, String dataFinal) {
        LocalDate inicio;
        try {
            inicio = LocalDate.parse(dataInicial, config.formaterPatternddMMyyyy());
        } catch (NullPointerException e) {
            inicio = LocalDate.now().withDayOfMonth(1);
        }
        LocalDate fim;
        try {
            fim = LocalDate.parse(dataFinal, config.formaterPatternddMMyyyy());
        } catch (NullPointerException e) {
            int ultimoDia = LocalDate.now().lengthOfMonth();
            fim = LocalDate.now().withDayOfMonth(ultimoDia);
        }

        //Obtendo o município do usuario
        String municipio = this.personaService.getMunicpioDoUsuario();

        //Obtendo o Escritório do usuario
        Escritorio eslocDoUsuario = this.personaService.getEslocDoUsuario();

        //Executando consulta
        List<Visita> allServicesManager = this.visitaRepository.findAllServicesManagerInicioFim(inicio.atTime(0, 0), fim.atTime(23, 59));

        List<AtividadesPrestadasVO> list = allServicesManager.stream()
                .map(data -> mapAtividadesPrestadasVO(data))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public CallDTOPost expirate(String id) {
        Call call = this.repository.findById(id).orElseThrow(() -> new CallNotFoundException("Chamada de serviço não encontrada"));
        call.setStatus(EnumStatus.EXPIRADA);
        call = this.repository.save(call);
        return this.toCallDTOPost(call);
    }

    @Override
    public ServicosReportVO getServicosByUserTodayReport() {
        //obtem o usuario logado
        //Buscando o usuario logado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;

        if (principal instanceof UserDetails) {
            nome = ((UserDetails) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        Persona usuario = this.personaService.findByCpf(nome);
        //obtem a data atual

        //////////////////////////////////////

        LocalDateTime inicio = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fim = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        String header_report = "Meus serviços de hoje";

        //buscando os dados no repositório
        List<Object[]> result = this.repository.findCallsReportPorPeriodoAndUsuario(inicio, fim, usuario);

        List<ServicoDetalheVO> list = result.stream().map(data -> mapServicoDetalheVO(data)).collect(Collectors.toList());

        ServicosReportVO servicoReport = ServicosReportVO.builder()
                .dataFinal(fim.format(config.formaterPatternddMMyyyy()))
                .dataInicial(inicio.format(config.formaterPatternddMMyyyy()))
                .build();

        servicoReport.setRelatorio(header_report);
        servicoReport.setServico(list);

        return servicoReport;


    }

    @Override
    public ServicosReportVO getDiarioServicos(int parseInt) {

        LocalDateTime inicio = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fim = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        String header_report = "Serviços prestados hoje neste escritório";

        //Recuperando o escritório no qual o usuário atual se encontra logado
        int codEsloc;
        //obtem o usuario logado
        //Buscando o usuario logado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;

        if (principal instanceof UserDetails) {
            nome = ((UserDetails) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        Persona usuario = this.personaService.findByCpf(nome);

        codEsloc = usuario.getEsloc().getCodigo();

        //buscando os dados no repositório
        List<Object[]> result = this.repository.findCallsReportPorPeriodo(inicio, fim, codEsloc);

        List<ServicoDetalheVO> list = result.stream().map(data -> mapServicoDetalheVO(data)).collect(Collectors.toList());

        ServicosReportVO servicoReport = ServicosReportVO.builder()
                .dataFinal(fim.format(config.formaterPatternddMMyyyy()))
                .dataInicial(inicio.format(config.formaterPatternddMMyyyy()))
                .build();

        servicoReport.setRelatorio(header_report);
        servicoReport.setServico(list);

        return servicoReport;
    }

    private AtividadesPrestadasVO mapAtividadesPrestadasVO(Visita data) {

        List<AcaoPrestadaVO> acoes = data.getChamadas().stream()
                .map(call -> mapAcaoPrestadaVO(call))
                .collect(Collectors.toList());

        List<ProdutorVO> produtores = data.getProdutores().stream()
                .map(prds -> mapProdutorVO(prds))
                .collect(Collectors.toList());

        Integer totalDeChamadas = acoes.size();

        return AtividadesPrestadasVO.builder()
                .codigoVisita(data.getCodigo())
                .local(data.getLocalDoAtendimento())
                .dataDaVisita(data.getDataDaVisita().format(config.formater()))
                .acoes(acoes)
                .produtores(produtores)
                .municipioVisita(data.getMunicipio())
                .totalDeChamadas(totalDeChamadas)
                .build();

    }

    private ProdutorVO mapProdutorVO(Persona produtores) {
        return ProdutorVO.builder()
                .cpf(produtores.getCpf())
                .nome(produtores.getNome())
                .endereco(produtores.getEndereco())
                .fone(produtores.getTelefone())
                .build();
    }

    private AcaoPrestadaVO mapAcaoPrestadaVO(Call data) {
        return AcaoPrestadaVO.builder()
                .codigo(data.getCodigo())
                .servico(data.getServico())
                .status(data.getStatus().toString())
                .tecnico(data.getResponsavel().getNome())
                .valor(data.getValor().toString())
                .dataDaChamada(data.getCreated().format(config.formater()))
                .build();
    }

    private ServicosPrestadosVO mapServicosPrestadosVO(Object[] data) {
        return ServicosPrestadosVO.builder()
                .dataServico(data[0].toString())
                .servico(data[1].toString())
                .cpfProdutor(data[2].toString())
                .enderecoProdutor(data[3].toString())
                .municipioProdutor(data[4].toString())
                .nomeProdutor(data[5].toString())
                .build();
    }

    private Object imprime(Call c) {

        return null;
    }


    private CallDTOView toCallDTOPostView(Call call) {
        String servicoQuitadoEm = null;
        try {
            servicoQuitadoEm = call.getServicoQuitadoEm().toString();
        } catch (NullPointerException ex) {

        }
        String cpf = null;
        try {
            cpf = call.getResponsavel().getCpf();

        } catch (NullPointerException ex) {

        }

        Visita codigo = call.getVisita();

        String visitaCodigo;

        try {
            visitaCodigo = codigo.getCodigo();
        } catch (NullPointerException ex) {
            visitaCodigo = null;
        }

        Visita vs = this.visitaRepository.findByCodigo(codigo.getCodigo()).get();

        //Definindo dados do produtor atendido
        String cpfProdutor = vs.getProdutores().get(0).getCpf();

        Persona produtor = this.personaService.findByCpf(cpfProdutor);

        return CallDTOView.builder()
                .valor(call.getValor())
                .status(call.getStatus().toString())
                .codigo(call.getCodigo())
                .dataDeConclusao(call.getPrevisaoDeConclusao())
                .dataPgto(call.getServicoQuitadoEm())
                .descricaoDoServico(call.getServico())
                .propriedadeRural(vs.getLocalDoAtendimento())
                .nomeDoProdutor(produtor.getNome())
                .build();
    }

}
