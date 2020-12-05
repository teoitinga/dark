package com.jp.dark.factory;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.enums.EnumStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CallFactory {

    public static CallDTO createCallDto() {

        return  CallDTO.builder()

                .build();
    }

    public static CallDTO createSavedCallDto() {
        return  CallDTO.builder()

                .build();

    }

    public static Call createCall() {
        return  Call.builder()

                .build();
    }

    public static List<CallDTOPost> createListWith3CallDTO() {
        List<CallDTOPost> list = new ArrayList<>();
        list.add(createAnyCallDTOPost());
        list.add(createOtherCallDTOPost());
        list.add(createThrirdCallDTOPost());

        return list;
    }
    public static CallDTOPost createAnyCallDTOPost(){
        return CallDTOPost.builder()
                .codigo("20201028")
                .CpfReponsavel("04459471604")
                .ocorrencia("Sem ocorrencias")
                .serviceProvidedCode("LM")
                .status("INICIADA")
                .servicoPrestado("Laudo de limite de credito")
                .valor(BigDecimal.valueOf(150))
                .build();
    }
    public static CallDTOPost createOtherCallDTOPost(){
        return CallDTOPost.builder()
                .codigo("20201029")
                .CpfReponsavel("04459471604")
                .ocorrencia("Sem ocorrencias também")
                .serviceProvidedCode("DAP")
                .status("INICIADA")
                .servicoPrestado("Emissão de DAP")
                .valor(BigDecimal.valueOf(0))
                .build();
    }
    public static CallDTOPost createThrirdCallDTOPost(){
        return CallDTOPost.builder()
                .codigo("20201030")
                .CpfReponsavel("04459471604")
                .ocorrencia("Sem ocorrencias mesmo também")
                .serviceProvidedCode("CAR")
                .status("INICIADA")
                .servicoPrestado("Elaboração de Cadastro Ambiental rural")
                .valor(BigDecimal.valueOf(200))
                .build();
    }

    public static List<Call> createListWith3Call() {
        List<Call> list = new ArrayList<>();
        list.add(createAnyCall());

        return list;

    }

    private static Call createAnyCall() {
        return Call.builder()
                .codigo("20201030")
                .servico("Elaboração de projeto")
                .previsaoDeConclusao(LocalDate.of(2020,12,22))
                .valor(BigDecimal.valueOf(180))
                .ocorrencia("Sem ocorrências")
                .status(EnumStatus.INICIADA)
                .serviceProvided(ServiceProvidedFactory.createServiceProvided())
                .responsavel(PersonaFactory.createValidPersona())
                .build();
    }
}
