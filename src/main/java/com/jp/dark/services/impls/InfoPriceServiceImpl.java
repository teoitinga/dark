package com.jp.dark.services.impls;

import com.jp.dark.dtos.PricesDTO;
import com.jp.dark.models.entities.InfoPrice;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.PricesItem;
import com.jp.dark.repository.InfoPriceRepository;
import com.jp.dark.services.InfoPriceService;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.PricesItemService;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InfoPriceServiceImpl implements InfoPriceService {
    private PersonaService personaService;
    private InfoPriceRepository repository;
    private PricesItemService pricesItemService;

    public InfoPriceServiceImpl(InfoPriceRepository repository, PersonaService personaService,
                                PricesItemService pricesItemService) {
        this.repository = repository;
        this.personaService = personaService;
        this.pricesItemService = pricesItemService;
    }

    @Override
    public PricesDTO save(PricesDTO dto) {
        InfoPrice info = toInfoPrice(dto);
        return dto;
    }

    @Override
    public InfoPrice toInfoPrice(PricesDTO dto) {
        InfoPrice response = new InfoPrice();

        Persona produtorInformante = this.personaService.findByCpf(dto.getCpfProdutorInformante());
        response.setProdutorInformante(produtorInformante);

        PricesItem item = this.pricesItemService.findById(dto.getEspecificacaoCod());
        response.setEspecificacao(item);

        int qtdPorUnid = dto.getQtdPorUnid();
        response.setQtdPorUnid(qtdPorUnid);

        BigDecimal valor = dto.getValorUnitario();
        response.setValor(valor);

        return response;
    }
}
