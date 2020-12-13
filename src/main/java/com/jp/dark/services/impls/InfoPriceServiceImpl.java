package com.jp.dark.services.impls;

import com.jp.dark.dtos.PricesDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.exceptions.CpfIsNotValidException;
import com.jp.dark.models.entities.InfoPrice;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.PricesItem;
import com.jp.dark.models.repository.InfoPriceRepository;
import com.jp.dark.services.InfoPriceService;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.PricesItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class InfoPriceServiceImpl implements InfoPriceService {
    private PersonaService personaService;
    private InfoPriceRepository repository;
    private PricesItemService pricesItemService;

    public InfoPriceServiceImpl(InfoPriceRepository repository,
                                PersonaService personaService,
                                PricesItemService pricesItemService
    ) {
        this.repository = repository;
        this.personaService = personaService;
        this.pricesItemService = pricesItemService;
    }

    @Override
    public PricesDTO save(PricesDTO dto) {
        InfoPrice info = toInfoPrice(dto);

        InfoPrice infoPrice = this.repository.save(info);

        return toInfoPriceDTO(infoPrice);
    }

    @Override
    public PricesDTO toInfoPriceDTO(InfoPrice inf) {
        ProdutorMinDTO produtor = ProdutorMinDTO.builder()
                .cpf(inf.getProdutorInformante().getCpf())
                .nome(inf.getProdutorInformante().getNome())
                .build();

        String dataDoLevantamento;
        try{
            dataDoLevantamento = inf.getCreated().toString();
        }catch (NullPointerException ex){
            dataDoLevantamento = null;
        }
        return PricesDTO.builder()
                .id(inf.getId())
                .qtdPorUnid(inf.getQtdPorUnid())
                .especificacaoCod(inf.getEspecificacao().getEspecificação())
                .produtorInformante(produtor)
                .valorUnitario(inf.getValor())
                .dataDoLevantamento(dataDoLevantamento)
                .build();
    }

    @Override
    public InfoPrice toInfoPrice(PricesDTO dto) {
        InfoPrice response = new InfoPrice();

        Persona produtorInformante;

        //verifica se a pessoa existe, se não for, então será registrada no banco de dados
        if(this.personaService.cpfIsValid(dto.getProdutorInformante().getCpf())){
            if(this.personaService.personaExists(dto.getProdutorInformante().getCpf())){
                produtorInformante = this.personaService.findByCpf(dto.getProdutorInformante().getCpf());
            }else {
                produtorInformante = this.personaService.save(dto.getProdutorInformante());
            }
        }else {
            throw new CpfIsNotValidException();
        }

        response.setProdutorInformante(produtorInformante);

        log.info("Especificação: {}", dto.getEspecificacaoCod());
        PricesItem item = this.pricesItemService.findById(dto.getEspecificacaoCod());
        response.setEspecificacao(item);

        int qtdPorUnid = dto.getQtdPorUnid();
        response.setQtdPorUnid(qtdPorUnid);

        BigDecimal valor = dto.getValorUnitario();
        response.setValor(valor);

        return response;
    }
}
