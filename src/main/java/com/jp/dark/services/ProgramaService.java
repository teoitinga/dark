package com.jp.dark.services;

import com.jp.dark.dtos.BeneficiarioDTO;
import com.jp.dark.dtos.MultiplosBeneficiariosDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.dtos.ProgramaDTO;
import com.jp.dark.models.entities.Beneficiario;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.Programa;

import java.util.List;

public interface ProgramaService {

    MultiplosBeneficiariosDTO save(MultiplosBeneficiariosDTO dto);

    Programa findByCodigo(String codigoDoPrograma);

    MultiplosBeneficiariosDTO verifyPrcodutores(MultiplosBeneficiariosDTO beneficiarios);

    BeneficiarioDTO toBeneficiarioDTO(Beneficiario prd);

    ProdutorMinDTO toProdutorMinDTO(Persona beneficiario);

    Beneficiario toBeneficiario(BeneficiarioDTO prd, Programa prg);

    Persona check(ProdutorMinDTO prd);

    boolean personaExists(String cpf);

    boolean cpfIsValid(String cpf);

    ProdutorMinDTO toProdutorMinDTO(BeneficiarioDTO beneficiario);

    List<ProgramaDTO> findByReferenciaContaining(String prg);

    ProgramaDTO createProgram(ProgramaDTO dto);

}
