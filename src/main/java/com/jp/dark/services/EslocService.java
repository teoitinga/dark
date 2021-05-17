package com.jp.dark.services;

import com.jp.dark.dtos.EslocDTO;
import com.jp.dark.models.entities.Escritorio;
import com.jp.dark.vos.UsuarioVO;

import java.util.List;

public interface EslocService {
    Escritorio findById(String codEsloc);

    EslocDTO save(EslocDTO dto);

    void addService(String codEsloc, String codService);

    void addUser(String codEsloc, String cpf);

    void removeUser(String codEsloc, String cpf);

    void removeService(String codEsloc, String codService);

    List<UsuarioVO> getUsersForEsloc(String codEsloc);

    List<EslocDTO> getAllEslocs();
}
