package com.jp.dark.factory;

import com.jp.dark.dtos.BeneficiarioDTO;
import com.jp.dark.dtos.MultiplosBeneficiariosDTO;
import com.jp.dark.models.entities.Beneficiario;

import java.util.ArrayList;
import java.util.List;

public class BeneficiarioFactory {
    public static BeneficiarioDTO createValidBeneficiario() {
        return BeneficiarioDTO.builder()
                .beneficiario(ProdutorFactory.createLucas())
                .codigoDoPrograma("1")
                .quantidade(5)
                .build();
    }
    public static BeneficiarioDTO createValidLaraBeneficiario() {
        return BeneficiarioDTO.builder()
                .beneficiario(ProdutorFactory.createLara())
                .codigoDoPrograma("1")
                .quantidade(2)
                .build();
    }
    public static BeneficiarioDTO createValidLaraBeneficiarioNoProgram() {
        return BeneficiarioDTO.builder()
                .beneficiario(ProdutorFactory.createLara())
                .codigoDoPrograma("1")
                .quantidade(2)
                .build();
    }
    public static BeneficiarioDTO createValidBryanBeneficiario() {
        return BeneficiarioDTO.builder()
                .beneficiario(ProdutorFactory.createBryan())
                .codigoDoPrograma("1")
                .quantidade(1)
                .build();
    }
    public static BeneficiarioDTO createValidBryanBeneficiarioNoProgram() {
        return BeneficiarioDTO.builder()
                .beneficiario(ProdutorFactory.createBryan())
                .quantidade(1)
                .build();
    }

    public static MultiplosBeneficiariosDTO createValidBeneficiariosMultiplos() {
        List<BeneficiarioDTO> benef = new ArrayList<>();
        benef.add(createValidBryanBeneficiario());
        benef.add(createValidLaraBeneficiario());

        return MultiplosBeneficiariosDTO.builder()
                .beneficiarios(benef)
                .codigoDoPrograma("2")
                .build();
    }
    public static MultiplosBeneficiariosDTO createValidBeneficiariosMultiplosNoProgram() {
        List<BeneficiarioDTO> benef = new ArrayList<>();
        benef.add(createValidBryanBeneficiarioNoProgram());
        benef.add(createValidLaraBeneficiarioNoProgram());

        return MultiplosBeneficiariosDTO.builder()
                .beneficiarios(benef)
                .build();
    }

    public static Beneficiario createBeneficiario() {
        return Beneficiario.builder()
                .beneficiario(ProdutorFactory.createValidBryan())
                .programa(ProgramaFactory.createNewProgram().get())
                .quantidade(1)
                .build();
    }
}
