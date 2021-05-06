package com.jp.dark.controllers;

import com.jp.dark.config.Config;
import com.jp.dark.services.JasperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/reports/pdf")
@Slf4j
@Api("Api de relatórios jasper")
public class ReportsController {

    private Connection connection;

    private JasperService jasperService;

    public ReportsController(Connection connection,
                             JasperService jasperService) {
        this.connection = connection;
        this.jasperService = jasperService;
    }

    @GetMapping("/atendimentos")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("GET a report atendimentos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    public void reportAtendimentos(
            @RequestParam("inicio") String inicio,
            @RequestParam("fim") String fim,
            @RequestParam("code") String code,
            @RequestParam("acao") String acao,
            HttpServletResponse response
    ) throws IOException, ParseException {
log.info(inicio);
log.info(fim);
log.info(acao);
log.info(code);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dtInicial = null;
        Date dtFinal = null;

        dtInicial = sdf.parse(inicio);
        dtFinal = sdf.parse(fim);

        this.jasperService.addParams("PERIODO_INICIAL", dtInicial);
        this.jasperService.addParams("PERIODO_FINAL", dtFinal);
    
    byte[] bytes = jasperService.exportaPDF(code);
    response.setContentType(MediaType.APPLICATION_PDF_VALUE);
    if(acao.equals("v")){
        response.setHeader("Content-disposition", "inLine; filename="+code+".pdf");
    }else{
        response.setHeader("Content-disposition", "attachment; filename="+code+".pdf");

    }
response.getOutputStream().write(bytes);
    }
}
