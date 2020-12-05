package com.jp.dark.services;

import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.models.entities.ServiceProvided;

public interface ServiceProvidedService {
    ServiceProvidedDTO save(ServiceProvidedDTO dto);

    ServiceProvidedDTO toServiceProvidedDTO(ServiceProvided saved);

    ServiceProvided toServiceProvided(ServiceProvidedDTO dto);

    ServiceProvided findByCodigoService(String servico);

    boolean serviceExists(String serviceProvidedCode);
}
