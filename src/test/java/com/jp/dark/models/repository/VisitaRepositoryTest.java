package com.jp.dark.models.repository;

import com.jp.dark.auditables.AuditConfiguration;
import com.jp.dark.auditables.AuditConfigurationTest;
import com.jp.dark.auditables.AuditorAwareImpl;
import com.jp.dark.factory.PersonaFactory;
import com.jp.dark.factory.ProdutorFactory;
import com.jp.dark.factory.ServiceProvidedFactory;
import com.jp.dark.factory.VisitaFactory;
import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.repository.ServiceProvidedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@Import(AuditConfigurationTest.class)
public class VisitaRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    VisitaRepository repository;

    @Autowired
    ServiceProvidedRepository serviceProvidedRepository;

    @Autowired
    PersonaRepository personaRepository;

    @BeforeEach
    public void setUp(){

    }
    @Test
    @DisplayName("Deve retornar verdadeiro quando existir uma visita registrada no banco de dados.")
    public void existsByCodigoTest(){
        //cenario
        String codigo = "20201104";

        //persisitndo as informações da visita
        Visita vs = VisitaFactory.createNewValidVisita();
        vs.setCodigo(codigo);

        entityManager.persist(vs);

        //execução
        boolean exists = repository.existsByCodigo(codigo);

        //verificações
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir uma visita registrada no banco de dados.")
    public void returnFalseWhenCodigoDoesNotExist(){
        //cenario
        String codigo = "20201104";

        //execução
        boolean exists = repository.existsByCodigo(codigo);

        //verificações
        assertThat(exists).isFalse();
    }
    @Test
    @DisplayName("Deve obter uma visita pelo Codigo")
    public void findByCodigoTest(){

        Visita visita = VisitaFactory.createNewValidVisita();
        String codigo = "2020211";

        visita.setCodigo(codigo);
        ServiceProvided serviceProvided = ServiceProvidedFactory.createServiceProvided();

        serviceProvided = serviceProvidedRepository.save(serviceProvided);

        personaRepository.save(ProdutorFactory.createValidLucas());
        personaRepository.save(ProdutorFactory.createValidMatheus());
        personaRepository.save(ProdutorFactory.createValidBryan());
        personaRepository.save(ProdutorFactory.createValidLara());
        personaRepository.save(ProdutorFactory.createValidRenata());

        entityManager.persist(visita);

        //execução
        Optional<Visita> foundVisita = repository.findByCodigo(codigo);

        //Verificações
        assertThat(foundVisita.isPresent()).isTrue();
        assertThat(foundVisita.get().getCodigo()).isEqualTo(codigo);
        assertThat(foundVisita.get().getSituacao()).isEqualTo("Pastagem degradada.");
        assertThat(foundVisita.get().getRecomendacao()).isEqualTo("Realizar analise de solo urgente.");
        assertThat(foundVisita.get().getCreatedBy()).isEqualTo("Test auditor");
        assertThat(foundVisita.get().getModifiedBy()).isEqualTo("Test auditor");
        assertThat(foundVisita.get().getCreated()).isBefore(LocalDateTime.now());

    }
    @Test
    @DisplayName("Deve salvar um registro de visita")
    public void saveTest(){
        Visita visita = VisitaFactory.createNewValidVisita();

        ServiceProvided serviceProvided = ServiceProvidedFactory.createServiceProvided();

        this.serviceProvidedRepository.save(serviceProvided);
        personaRepository.save(ProdutorFactory.createValidLucas());
        personaRepository.save(ProdutorFactory.createValidMatheus());
        personaRepository.save(ProdutorFactory.createValidBryan());
        personaRepository.save(ProdutorFactory.createValidLara());
        personaRepository.save(ProdutorFactory.createValidRenata());

        Visita saved = repository.save(visita);

        assertThat(saved).isNotNull();
        assertThat(saved.getCodigo()).isEqualTo(visita.getCodigo());
    }
    @Test
    @DisplayName("Deve deletar um registro de visita")
    public void deleteTest(){

        String codigo = "2020211";
        Visita visita = VisitaFactory.createNewValidVisita();
        visita.setCodigo(codigo);

        entityManager.persist(visita);
        Visita foundVisita = entityManager.find(Visita.class, visita.getCodigo());

        repository.delete(foundVisita);

        Visita deletedVisita = entityManager.find(Visita.class, visita.getCodigo());

        assertThat(deletedVisita).isNull();

    }
}
