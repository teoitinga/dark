package com.jp.dark.services;

import com.jp.dark.dtos.BeneficiarioDTO;
import com.jp.dark.dtos.MultiplosBeneficiariosDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.models.entities.Beneficiario;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.Programa;

public interface ProgramaService {

    MultiplosBeneficiariosDTO save(MultiplosBeneficiariosDTO dto);

    Programa findByCodigo(Integer codigoDoPrograma);

    MultiplosBeneficiariosDTO verifyPrcodutores(MultiplosBeneficiariosDTO beneficiarios);

    BeneficiarioDTO toBeneficiarioDTO(Beneficiario prd);

    ProdutorMinDTO toProdutorMinDTO(Persona beneficiario);

    Beneficiario toBeneficiario(BeneficiarioDTO prd, Programa prg);

    Persona check(ProdutorMinDTO prd);

    boolean personaExists(String cpf);

    boolean cpfIsValid(String cpf);

    ProdutorMinDTO toProdutorMinDTO(BeneficiarioDTO beneficiario);
}
