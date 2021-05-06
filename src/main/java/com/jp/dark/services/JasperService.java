package com.jp.dark.services;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JasperService {

    private static final String JASPER_DIRETORIO = "classpath:";
    private static final String JASPER_PREFIXO = "";
    private static final String JASPER_SUFIXO = ".jasper";

    private Connection connection;

    private Map<String, Object> params = new HashMap<>();

    public JasperService(Connection connection) {
        this.connection = connection;
    }

    public void addParams(String key, Object value){
        this.params.put(key, value);
    }

    public byte[] exportaPDF(String code){

        byte[] bytes = null;
        try {
            log.info(params.toString());
            File file = ResourceUtils.getFile(JASPER_DIRETORIO.concat(code).concat(JASPER_SUFIXO));
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), params, connection);
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return bytes;

    }

}
