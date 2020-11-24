package com.jp.dark.services;

import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.models.entities.ServiceProvided;

public interface ServiceProvidedService {
    ServiceProvided toServiceProvided(ServiceProvidedDTO serviceProvided);

    ServiceProvided toServiceProvidedNoContent(ServiceProvidedDTO service);

    ServiceProvidedDTO toServiceProvidedDTO(ServiceProvided serviceProvided);

    ServiceProvidedDTO save(ServiceProvidedDTO dto);
}
