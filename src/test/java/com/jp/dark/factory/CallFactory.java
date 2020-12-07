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
                .codigoDaVisita("2001")
                .servico("Servicao")
                .serviceProvidedCode("LM")
                .ocorrencia("No ocurrency")
                .status(EnumStatus.CANCELADA.toString())
                .CpfReponsavel("04459471604")
                .valor(BigDecimal.valueOf(159))
                .build();
    }

    public static CallDTO createSavedCallDto() {
        return  CallDTO.builder()
                .codigoDaVisita("2001")
                .servico("Servicao")
                .serviceProvidedCode("LM")
                .ocorrencia("No ocurrency")
                .status(EnumStatus.CANCELADA.toString())
                .CpfReponsavel("04459471604")
                .valor(BigDecimal.valueOf(159))
                .build();

    }

    public static Call createCall() {
        return  Call.builder()
                .codigo("2001")
                .servico("Servicao")
                .serviceProvided(ServiceProvidedFactory.createServiceProvided())
                .ocorrencia("No ocurrency")
                .status(EnumStatus.CANCELADA)
                .responsavel(PersonaFactory.createValidPersona())
                .valor(BigDecimal.valueOf(159))
                .previsaoDeConclusao(LocalDate.of(2021,01,04))
                .visita(VisitaFactory.createVisitaEntity())
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
        list.add(createOtherCall());
        list.add(createThirdCall());

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
    private static Call createOtherCall() {
        return Call.builder()
                .codigo("20201030")
                .servico("Elaboração de CAR")
                .previsaoDeConclusao(LocalDate.of(2020,12,22))
                .valor(BigDecimal.valueOf(0))
                .ocorrencia("Sem ocorrências***")
                .status(EnumStatus.INICIADA)
                .serviceProvided(ServiceProvidedFactory.createServiceProvided())
                .responsavel(PersonaFactory.createValidPersona())
                .build();
    }
    private static Call createThirdCall() {
        return Call.builder()
                .codigo("20201030")
                .servico("Elaboração de outro projeto")
                .previsaoDeConclusao(LocalDate.of(2020,12,22))
                .valor(BigDecimal.valueOf(180))
                .ocorrencia("Sem ocorrências")
                .status(EnumStatus.INICIADA)
                .serviceProvided(ServiceProvidedFactory.createServiceProvided())
                .responsavel(PersonaFactory.createValidPersona())
                .build();
    }
}
