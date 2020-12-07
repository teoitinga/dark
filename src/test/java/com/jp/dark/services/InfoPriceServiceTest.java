package com.jp.dark.services;

import com.jp.dark.dtos.PricesDTO;
import com.jp.dark.factory.PersonaFactory;
import com.jp.dark.factory.PricesFactory;
import com.jp.dark.models.entities.InfoPrice;
import com.jp.dark.models.repository.InfoPriceRepository;
import com.jp.dark.services.impls.InfoPriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class InfoPriceServiceTest {

    InfoPriceService service;

    @MockBean
    InfoPriceRepository repository;

    @MockBean
    PersonaService personaService;
    @MockBean
    PricesItemService pricesItemService;

    @BeforeEach
    public void setup(){
        this.service = new InfoPriceServiceImpl(repository, personaService,
                pricesItemService);
    }
    @Test
    @DisplayName("Deve registrar uma informação de preço com a data atual")
    public void saveTest(){
        PricesDTO dto = PricesFactory.createPriceBoiGordo();
        PricesDTO pricesDTO = this.service.save(dto);

        assertThat(pricesDTO.getEspecificacaoCod()).isEqualTo("4");
    }
    @Test
    @DisplayName("Deve testar a conversão de PricesDTO dto para InfoPrice.")
    public void toInfoPriceTest(){
        PricesDTO dto = PricesFactory.createPriceBoiGordo();

        Mockito.when(this.personaService.findByCpf(Mockito.anyString()))
                .thenReturn(PersonaFactory.createValidPersona());

        Mockito.when(this.pricesItemService.findById(Mockito.anyString()))
                .thenReturn(PricesFactory.createPriceItem());

        //Execução
        InfoPrice prices = this.service.toInfoPrice(dto);

        assertThat(prices).isNotNull();
        assertThat(prices.getValor()).isEqualTo(BigDecimal.valueOf(210.22));
        assertThat(prices.getQtdPorUnid()).isEqualTo(16);
    }
}
