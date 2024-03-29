package com.jp.dark.services.impls;

import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.exceptions.ServiceProvidedAlreadyException;
import com.jp.dark.exceptions.ServiceProvidedNotFoundException;
import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.models.repository.ServiceProvidedRepository;
import com.jp.dark.services.ServiceProvidedService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceProvidedServiceImpl implements ServiceProvidedService {

    private final ServiceProvidedRepository repository;

    public ServiceProvidedServiceImpl(ServiceProvidedRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceProvidedDTO save(ServiceProvidedDTO dto) {
        //Verifica se já existe

        ServiceProvided serviceProvided;
        if(serviceExists(dto.getCodigo())){
            throw new ServiceProvidedAlreadyException();
        }

        serviceProvided = toServiceProvided(dto);

        ServiceProvided saved = this.repository.save(serviceProvided);
        return toServiceProvidedDTO(saved);
    }
    @Override
    public ServiceProvidedDTO toServiceProvidedDTO(ServiceProvided service) {
        return ServiceProvidedDTO.builder()
                .timeRemaining(service.getTimeRemaining())
                .referency(service.getReferency())
                .descricao(service.getDescricao())
                .defaultValue(service.getDefaultValue())
                .codigo(service.getCodigo())
                .build();
    }
    @Override
    public ServiceProvided toServiceProvided(ServiceProvidedDTO service) {
        return ServiceProvided.builder()
                .timeRemaining(service.getTimeRemaining())
                .referency(service.getReferency())
                .descricao(service.getDescricao())
                .defaultValue(service.getDefaultValue())
                .codigo(service.getCodigo())
                .build();
    }

    @Override
    public ServiceProvided findByCodigoService(String servico) {
        return repository.findByCodigo(servico).orElseThrow(()-> new ServiceProvidedNotFoundException(servico));
    }

    @Override
    public boolean serviceExists(String serviceProvidedCode) {
        return this.repository.existsByCodigo(serviceProvidedCode);
    }

    @Override
    public List<ServiceProvidedDTO> findByServiceContaining(String srv) {

        List<ServiceProvided> result = this.repository.findByReferencyContainingIgnoreCase(srv);
        List<ServiceProvidedDTO> dto = result.stream()
                                        .map(service->toServiceProvidedDTO(service))
                                            .collect(Collectors.toList());
        return dto;
    }

    @Override
    public List<ServiceProvidedDTO> findByServiceEslocs(String srv, String codEsloc) {

        List<ServiceProvided> result = this.repository.findServicesByReferencyEsloc(srv, Integer.parseInt(codEsloc));
        List<ServiceProvidedDTO> dto = result.stream()
                                        .map(service->toServiceProvidedDTO(service))
                                            .collect(Collectors.toList());
        return dto;
    }
}
