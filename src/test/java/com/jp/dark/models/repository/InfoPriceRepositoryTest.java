package com.jp.dark.models.repository;

import com.jp.dark.auditables.AuditConfigurationTest;
import com.jp.dark.factory.PersonaFactory;
import com.jp.dark.factory.PricesFactory;
import com.jp.dark.models.entities.InfoPrice;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.PricesItem;
import com.jp.dark.repository.InfoPriceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@Import(AuditConfigurationTest.class)
public class InfoPriceRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    InfoPriceRepository repository;

    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    PriceItemRepository pricesItemRepository;


    @Test
    @DisplayName("Deve salvar um registro de preco de Boi Gordo")
    public void saveTest(){
        InfoPrice price = PricesFactory.createValidInfoPrice();

        PricesItem priceItem = PricesFactory.createPriceItem();
        Persona persona = PersonaFactory.createValidPersona();

        persona = this.personaRepository.save(persona);
        priceItem = this.pricesItemRepository.save(priceItem);

        InfoPrice savedPrice = repository.save(price);

        assertThat(savedPrice).isNotNull();
        assertThat(savedPrice.getEspecificacao()).isEqualTo(price.getEspecificacao());
        assertThat(savedPrice.getQtdPorUnid()).isEqualTo(15);
        assertThat(savedPrice.getProdutorInformante()).isEqualTo(persona);
        assertThat(savedPrice.getValor()).isEqualTo(BigDecimal.valueOf(320.50));
    }
}
