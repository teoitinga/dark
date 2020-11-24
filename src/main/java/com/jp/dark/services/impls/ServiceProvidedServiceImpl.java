package com.jp.dark.services.impls;

import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.exceptions.ServiceProvidedNotFoundException;
import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.repository.ServiceProvidedRepository;
import com.jp.dark.services.ServiceProvidedService;

public class ServiceProvidedServiceImpl implements ServiceProvidedService {

    private final ServiceProvidedRepository repository;

    public ServiceProvidedServiceImpl(ServiceProvidedRepository serviceProvidedRepository) {
        this.repository = serviceProvidedRepository;
    }

    @Override
    public ServiceProvided toServiceProvided(ServiceProvidedDTO serviceProvided) {
        return this.repository.findByCodigo(serviceProvided.getCodigo())
                .orElseThrow(()->new ServiceProvidedNotFoundException());
    }
    @Override
    public ServiceProvided toServiceProvidedNoContent(ServiceProvidedDTO service) {
        return ServiceProvided.builder()
                .defaultValue(service.getDefaultValue())
                .timeRemaining(service.getTimeRemaining())
                .referency(service.getReferency())
                .descricao(service.getDescricao())
                .codigo(service.getCodigo())
                .build();
    }

    @Override
    public ServiceProvidedDTO toServiceProvidedDTO(ServiceProvided serviceProvided) {
        return ServiceProvidedDTO.builder()
                .codigo(serviceProvided.getCodigo())
                .defaultValue(serviceProvided.getDefaultValue())
                .descricao(serviceProvided.getDescricao())
                .referency(serviceProvided.getReferency())
                .timeRemaining(serviceProvided.getTimeRemaining())
                .build();
    }

    @Override
    public ServiceProvidedDTO save(ServiceProvidedDTO dto) {
        ServiceProvided service = this.toServiceProvidedNoContent(dto);
        service = this.repository.save(service);
        ServiceProvidedDTO response = this.toServiceProvidedDTO(service);
        return response;
    }
}
