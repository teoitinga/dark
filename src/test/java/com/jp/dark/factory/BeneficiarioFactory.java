package com.jp.dark.factory;

import com.jp.dark.dtos.BeneficiarioDTO;
import com.jp.dark.dtos.MultiplosBeneficiariosDTO;

import java.util.ArrayList;
import java.util.List;

public class BeneficiarioFactory {
    public static BeneficiarioDTO createValidBeneficiario() {
        return BeneficiarioDTO.builder()
                .cpfBeneficiario(PersonaFactory.createValidPersona().getCpf())
                .codigoDoPrograma(1)
                .quantidade(5)
                .build();
    }
    public static BeneficiarioDTO createValidLaraBeneficiario() {
        return BeneficiarioDTO.builder()
                .cpfBeneficiario(ProdutorFactory.createValidLara().getCpf())
                .codigoDoPrograma(1)
                .quantidade(2)
                .build();
    }
    public static BeneficiarioDTO createValidBryanBeneficiario() {
        return BeneficiarioDTO.builder()
                .cpfBeneficiario(ProdutorFactory.createValidBryan().getCpf())
                .codigoDoPrograma(1)
                .quantidade(1)
                .build();
    }

    public static MultiplosBeneficiariosDTO createValidBeneficiariosMultiplos() {
        List<BeneficiarioDTO> benef = new ArrayList<>();
        benef.add(createValidBryanBeneficiario());
        benef.add(createValidLaraBeneficiario());

        return MultiplosBeneficiariosDTO.builder()
                .beneficiarios(benef)
                .codigoDoPrograma(1)
                .build();
    }
    public static MultiplosBeneficiariosDTO createValidBeneficiariosMultiplosNoProgram() {
        List<BeneficiarioDTO> benef = new ArrayList<>();
        benef.add(createValidBryanBeneficiario());
        benef.add(createValidLaraBeneficiario());

        return MultiplosBeneficiariosDTO.builder()
                .beneficiarios(benef)
                .build();
    }

}
