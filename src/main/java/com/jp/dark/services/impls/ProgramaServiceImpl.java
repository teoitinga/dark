package com.jp.dark.services.impls;

import com.jp.dark.dtos.BeneficiarioDTO;
import com.jp.dark.dtos.MultiplosBeneficiariosDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.dtos.ProgramaDTO;
import com.jp.dark.exceptions.CpfIsNotValidException;
import com.jp.dark.exceptions.ProgramaNotFoundException;
import com.jp.dark.models.entities.Beneficiario;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.Programa;
import com.jp.dark.models.repository.BeneficiarioRepository;
import com.jp.dark.models.repository.ProgramaRepository;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.ProgramaService;
import com.jp.dark.utils.Generates;
import com.jp.dark.utils.GeraCpfCnpj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class ProgramaServiceImpl implements ProgramaService {

    private ProgramaRepository repository;
    private PersonaService personaService;
    private BeneficiarioRepository beneficiarioRepository;

    public ProgramaServiceImpl(ProgramaRepository repository, PersonaService personaService,
                               BeneficiarioRepository beneficiarioRepository) {

        this.repository = repository;
        this.personaService = personaService;
        this.beneficiarioRepository = beneficiarioRepository;
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

        List<Beneficiario> benef = beneficiarios.getBeneficiarios().stream()
                .map(prd->toBeneficiario(prd, programa))
                .collect(Collectors.toList());

        List<BeneficiarioDTO> benefDto = benef.stream()
                .map(prd->toBeneficiarioDTO(prd))
                .collect(Collectors.toList());

        MultiplosBeneficiariosDTO response = new MultiplosBeneficiariosDTO();
        response.setBeneficiarios(benefDto);

        return response;
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

        List<Programa> result = this.repository.findByReferenciaContainingIgnoreCase(prg);
        List<ProgramaDTO> list = result.stream().map(entity->toProgramaDTO(entity)).collect(Collectors.toList());
        return list;
    }

    @Override
    public ProgramaDTO createProgram(ProgramaDTO dto) {

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
        return ProgramaDTO.builder()
                .nome(programa.getNome())
                .referencia(programa.getReferencia())
                .descricao(programa.getDescricao())
                .DataInicio(programa.getDataInicio())
                .DataFim(programa.getDataFim())
                .codigo(codigo)
                .build();
    }

    private Programa toPrograma(ProgramaDTO dto) {

        String codigo;
        try{
            codigo = dto.getCodigo();
        }catch (NullPointerException exc){
            codigo = Generates.keyCode(Generates.createNumber());
        }

        if(dto.getCodigo() == null){
            codigo = Generates.keyCode(Generates.createNumber());
        }

        return Programa.builder()
                .nome(dto.getNome())
                .referencia(dto.getReferencia())
                .descricao(dto.getDescricao())
                .DataInicio(dto.getDataInicio())
                .DataFim(dto.getDataFim())
                .codigo(codigo)
                .build();
    }

}
