package com.jp.dark.models.repository;

import com.jp.dark.auditables.AuditConfigurationTest;
import com.jp.dark.factory.ProdutorFactory;
import com.jp.dark.factory.ServiceProvidedFactory;
import com.jp.dark.factory.VisitaFactory;
import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.models.entities.Visita;
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


}
