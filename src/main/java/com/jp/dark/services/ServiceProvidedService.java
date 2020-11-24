package com.jp.dark.services;

import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.models.entities.ServiceProvided;

public interface ServiceProvidedService {
    ServiceProvided toServiceProvided(ServiceProvidedDTO serviceProvided);

    ServiceProvidedDTO toServiceProvidedDTO(ServiceProvided serviceProvided);
}
