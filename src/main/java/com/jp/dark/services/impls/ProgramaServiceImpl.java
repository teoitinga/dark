package com.jp.dark.services.impls;

import com.jp.dark.dtos.*;
import com.jp.dark.exceptions.CpfIsNotValidException;
import com.jp.dark.exceptions.ProgramaNotFoundException;
import com.jp.dark.exceptions.ServiceProvidedNotFoundException;
import com.jp.dark.models.entities.*;
import com.jp.dark.models.enums.EnumStatus;
import com.jp.dark.models.repository.BeneficiarioRepository;
import com.jp.dark.models.repository.ProgramaRepository;
import com.jp.dark.services.*;
import com.jp.dark.utils.Generates;
import com.jp.dark.utils.GeraCpfCnpj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class ProgramaServiceImpl implements ProgramaService {

    private ProgramaRepository repository;
    private PersonaService personaService;
    private BeneficiarioRepository beneficiarioRepository;
    private ServiceProvidedService serviceProvided;
    private CallServiceImpl callService;
    private VisitaService visitaService;

    public ProgramaServiceImpl(ProgramaRepository repository, PersonaService personaService,
                               BeneficiarioRepository beneficiarioRepository,
                               ServiceProvidedService serviceProvided,
                               CallServiceImpl callService,
                               VisitaService visitaService) {

        this.repository = repository;
        this.personaService = personaService;
        this.beneficiarioRepository = beneficiarioRepository;
        this.serviceProvided = serviceProvided;
        this.callService = callService;
        this.visitaService = visitaService;
    }

    @Override
    public MultiplosBeneficiariosDTO save(MultiplosBeneficiariosDTO dto) {
        /*
        1- Verifica consistencia das informações dos produtores
         */
        MultiplosBeneficiariosDTO produtores = this.verifyPrcodutores(dto);


        return produtores;
    }

    @Override
    public Programa findByCodigo(String codigoDoPrograma) {
        return this.repository.findByCodigo(codigoDoPrograma).orElseThrow(()->new ProgramaNotFoundException());
    }

    @Override
    public MultiplosBeneficiariosDTO verifyPrcodutores(MultiplosBeneficiariosDTO beneficiarios) {

        Programa programa = this.findByCodigo(beneficiarios.getCodigoDoPrograma());

        //Obtem a lista de serviços prestados a este programa
        List<ServiceProvided> prestedServices = extractCalls(programa.getCodigo());

        //Configurando as chamadas de servico a serem registradas
        List<CallDTOPost> callDTOPosts = prestedServices.stream().map(srv -> {
            return CallDTOPost.builder()
                    .serviceProvidedCode(srv.getCodigo())
                    .valor(BigDecimal.ZERO)
                    .status(EnumStatus.FINALIZADA.toString())
                    .servicoPrestado(srv.getReferency())
                    .ocorrencia(programa.getNome())
                    .servicoQuitadoEm(LocalDate.now().toString())
                    .CpfReponsavel(this.personaService.getCpfDoUsuario())
                    .build();
        }).collect(Collectors.toList());

        //Obtendo a lista de beneficiario
        List<Beneficiario> benef = beneficiarios.getBeneficiarios().stream()
                .map(prd->toBeneficiario(prd, programa))
                .collect(Collectors.toList());

        List<BeneficiarioDTO> benefDto = benef.stream()
                .map(prd->toBeneficiarioDTO(prd))
                .collect(Collectors.toList());

        MultiplosBeneficiariosDTO response = new MultiplosBeneficiariosDTO();
        response.setBeneficiarios(benefDto);


        //Salvando registros de beneficiarios

        //Registrando a visita
        Visita vs = Visita.builder()
                .municipio(programa.getMunicipio())
                .situacao(programa.getDescricao())
                .localDoAtendimento(programa.getMunicipio())
                .dataDaVisita(LocalDate.now())
                .produtores(this.personaService.toListPersona(benefDto.stream()
                        .map(bnf->bnf.getBeneficiario())
                        .collect(Collectors.toList())))
                .chamadas(this.callService.toCall(callDTOPosts))
                .build();
        VisitaDTO visitaDTO = this.visitaService.toVisitaDTO(vs);
        visitaDTO = this.visitaService.save(visitaDTO);

        return response;
    }

    private List<ServiceProvided> extractCalls(String codigo) {
        String[] callsCode = codigo.split("-");
        List<ServiceProvided> callsResponse = new ArrayList<>();
        for (String call: callsCode
             ) {
            try{
            callsResponse.add(this.serviceProvided.findByCodigoService(call));
            }catch (ServiceProvidedNotFoundException ex){
            }
        }
        return callsResponse;
    }

    @Override
    public BeneficiarioDTO toBeneficiarioDTO(Beneficiario prd) {

        ProdutorMinDTO beneficiario;

        try{
            beneficiario = toProdutorMinDTO(prd.getBeneficiario());
        }catch (NullPointerException ex){
             beneficiario = null;
        }

        String codigoDoPrograma;

        try{
            codigoDoPrograma = prd.getPrograma().getCodigo();
        }catch (NullPointerException ex){
            codigoDoPrograma = null;
        }

        Integer quantidade;
        try{
            quantidade = prd.getQuantidade();
        }catch (NullPointerException ex){
            quantidade = null;
        }

        return BeneficiarioDTO.builder()
                .beneficiario(beneficiario)
                .codigoDoPrograma(codigoDoPrograma)
                .quantidade(quantidade)
                .observacoes(prd.getObservacoes())
                .id(prd.getId())
                .build();
    }

    @Override
    public ProdutorMinDTO toProdutorMinDTO(Persona beneficiario) {
        return ProdutorMinDTO.builder()
                .nome(beneficiario.getNome())
                .cpf(beneficiario.getCpf())
                .build();
    }

    @Override
    public Beneficiario toBeneficiario(BeneficiarioDTO prd, Programa programa) {
        ProdutorMinDTO produtorMin = toProdutorMinDTO(prd);
        Persona produtor;

        if(cpfIsValid(produtorMin.getCpf())){
            produtor = check(produtorMin);

            Beneficiario beneficiario = Beneficiario.builder()
                    .observacoes(prd.getObservacoes())
                    .beneficiario(produtor)
                    .programa(programa)
                    .quantidade(prd.getQuantidade())
                    .build();

            if(beneficiario.getId() == null){
                String keyCode = Generates.keyCode(Generates.createNumber());

                beneficiario.setId(keyCode);
            }
            beneficiario = this.beneficiarioRepository.save(beneficiario);
            return beneficiario;
        }else{
            throw new CpfIsNotValidException();
        }

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
    public ProdutorMinDTO toProdutorMinDTO(BeneficiarioDTO beneficiario) {
        ProdutorMinDTO produtor = beneficiario.getBeneficiario();
        return produtor;
    }

    @Override
    public List<ProgramaDTO> findByReferenciaContaining(String prg) {

        String municipio = this.personaService.getMunicpioDoUsuario();
        List<Programa> result = this.repository.findActiveProgramsByReferenciaContainingIgnoreCase(prg, municipio);
        List<ProgramaDTO> list = result.stream().map(entity->toProgramaDTO(entity)).collect(Collectors.toList());
        return list;
    }

    @Override
    public ProgramaDTO createProgram(ProgramaDTO dto) {

        String municipio = dto.getMunicipio();
        dto.setMunicipio(municipio);

        StringBuilder codigo = new StringBuilder();
        dto.getCodServicos().forEach(srv->{
            codigo.append(srv);
            codigo.append("-");
        });

        dto.setCodigo(Generates.keyCode(codigo.toString().substring(0, codigo.length() - 1)));

        Programa programa = toPrograma(dto);
        programa = this.repository.save(programa);
        ProgramaDTO saved = toProgramaDTO(programa);
        return saved;
    }

    private ProgramaDTO toProgramaDTO(Programa programa) {

        String codigo;
        try{
            codigo = programa.getCodigo();
        }catch (NullPointerException exc){
            codigo = null;
        }

        List<String> servicosCode = new ArrayList<>();
        for (String cod : programa.getCodigo().split("-")) {
            try{
            ServiceProvided codigoServicos = this.serviceProvided.findByCodigoService(cod);
            servicosCode.add(codigoServicos.getCodigo());

            }catch (ServiceProvidedNotFoundException exc){

            }
        }

        return ProgramaDTO.builder()
                .nome(programa.getNome())
                .referencia(programa.getReferencia())
                .descricao(programa.getDescricao())
                .DataInicio(programa.getDataInicio())
                .DataFim(programa.getDataFim())
                .municipio(programa.getMunicipio())
                .codServicos(servicosCode)
                .codigo(codigo)
                .build();
    }

    private Programa toPrograma(ProgramaDTO dto) {

        String codigo;
        try{
            codigo = dto.getCodigo();
        }catch (NullPointerException exc){
            codigo = null;//Generates.keyCode(Generates.createNumber());
        }

        return Programa.builder()
                .nome(dto.getNome())
                .referencia(dto.getReferencia())
                .descricao(dto.getDescricao())
                .DataInicio(dto.getDataInicio())
                .DataFim(dto.getDataFim())
                .municipio(dto.getMunicipio())
                .codigo(codigo)
                .build();
    }

}
