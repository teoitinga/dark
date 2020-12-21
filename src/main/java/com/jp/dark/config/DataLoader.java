package com.jp.dark.config;

import com.jp.dark.controllers.CallController;
import com.jp.dark.models.entities.*;
import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import com.jp.dark.models.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader {

    private final PersonaRepository personaRepository;

    private ServiceProvidedRepository serviceRepository;
    private ItemProducaoRepository itemProducaoRepository;
    private OrigemRendaRepository origemRepository;
    private PricesItemRepository pricesItemRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(ServiceProvidedRepository serviceRepository,
                      PersonaRepository personaRepository,
                      ItemProducaoRepository itemProducaoRepository,
                      OrigemRendaRepository origemRepository,
                      PricesItemRepository pricesItemRepository,
                      PasswordEncoder passwordEncoder
    ) {
        this.passwordEncoder = passwordEncoder;
        this.serviceRepository = serviceRepository;
        this.personaRepository = personaRepository;
        this.itemProducaoRepository = itemProducaoRepository;
        this.origemRepository = origemRepository;
        this.pricesItemRepository = pricesItemRepository;

        LoadServicesProvided();
        LoadUsers();
        LoadItensProducao();
    }

    private void LoadUsers() {
        this.personaRepository.save(new Persona("04459471604", "João Paulo Santana Gusmão", "33999065029", LocalDate.of(1979,1,4),"Rua José Tonel, 56",
                "35140000","Tarumirim",this.passwordEncoder.encode("jacare"), EnumCategoria.UNDEFINED, EnumPermissao.TECNICO, true));
    }

    private void LoadServicesProvided() {
        serviceRepository.save(new ServiceProvided("LAS", "Licenciamento ambiental", "Licenciamento", new BigDecimal(200), 7));
        serviceRepository.save(new ServiceProvided("LMBB", "BB Limite de credito", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("LMSC", "SICOOB Limite de credito", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("CRSC", "SICOOB projeto de crédito rural", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("CRBB", "BB PRONAF projeto de crédito rural", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("CRBBCS", "BB CUSTEIO projeto de crédito rural", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("CRBBPRN", "BB PRONAMP projeto de crédito rural", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("CRBBPRNCS", "BB CUSTEIO projeto de crédito rural", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("CRBBMDA", "BB MODERAGRO projeto de crédito rural", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("CRBBMDACS", "BB CUSTEIO projeto de crédito rural", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("CRBBINVAG", "BB INVESTAGRO projeto de crédito rural", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("CRBBINVAGCS", "BB CUSTEIO projeto de crédito rural", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("ATERBOV", "Ater Bovinocultura", "Assist. Pastagem", new BigDecimal(150), 1));
        serviceRepository.save(new ServiceProvided("ATERBOVPAST", "Ater pastagem", "Assist. Pastagem", new BigDecimal(150), 1));
        serviceRepository.save(new ServiceProvided("ATERBOVIRRG", "Ater irrigação", "Assist. irrigação", new BigDecimal(150), 1));
        serviceRepository.save(new ServiceProvided("ATERCULT", "Ater cultura", "Assist. culturas", new BigDecimal(0), 1));
        serviceRepository.save(new ServiceProvided("ATERCULTMILHO", "Ater cultura", "Assist. culturas", new BigDecimal(0), 1));
        serviceRepository.save(new ServiceProvided("ATERCULTFEIJAO", "Ater cultura", "Assist. culturas", new BigDecimal(0), 1));
        serviceRepository.save(new ServiceProvided("ATERCULTHORTA", "Ater cultura", "Assist. culturas", new BigDecimal(0), 1));
        serviceRepository.save(new ServiceProvided("ATERMAFSSPT", "Ater Meio ambienta", "Assist. culturas", new BigDecimal(0), 1));
        serviceRepository.save(new ServiceProvided("ATERMARESIDSOLID", "Tratamento de rasíduos sólidos", "Assist. culturas", new BigDecimal(0), 1));
        serviceRepository.save(new ServiceProvided("ATERMAPRTNSC", "Proteção de nascentes", "Assist. culturas", new BigDecimal(0), 1));
        serviceRepository.save(new ServiceProvided("ATERMACAR", "Emissão de Cadastro ambiental rural", "Elaboração de CAR", new BigDecimal(200), 5));
        serviceRepository.save(new ServiceProvided("ATERMACARRET", "Retificação de Cadastro ambiental rural", "Retificação de CAR", new BigDecimal(200), 5));
        serviceRepository.save(new ServiceProvided("ATERMACAR2V", "Segunda via de Cadastro ambiental rural", "2 via de CAR", new BigDecimal(50), 5));
        serviceRepository.save(new ServiceProvided("DAP", "Emissão de DAP", "Emissão de DAP", new BigDecimal(0), 2));
        serviceRepository.save(new ServiceProvided("DAP2V", "Emissão de 2 via de DAP", "DAP 2 via", new BigDecimal(0), 2));
    }

    private void LoadItensProducao(){
        OrigemRenda origemAgro = new OrigemRenda("AGRO", "Agricola","Produção obtida da agricultura familiar");
        OrigemRenda origemIndustria = new OrigemRenda("IND", "Agroindustria","Produção obtida da agricultura familiar");
        OrigemRenda origemPecuaria = new OrigemRenda("PEC", "Pecuaria","Produção obtida da agricultura familiar");
        OrigemRenda origemOutras = new OrigemRenda("OUTRND", "Outras rendas","Renda obtida fora do estabelecimento familiar");

        origemAgro = origemRepository.save(origemAgro);
        origemIndustria = origemRepository.save(origemIndustria);
        origemPecuaria = origemRepository.save(origemPecuaria);
        origemOutras = origemRepository.save(origemOutras);

        ItemProducao leite;
        ItemProducao bezerroMagro;
        ItemProducao boiMagro;
        ItemProducao boiGordo;

        itemProducaoRepository.save(new ItemProducao("QJ", "Queijos","Queijos","Queijos", BigDecimal.valueOf(210), origemIndustria));
        itemProducaoRepository.save(new ItemProducao("RAP", "Rapadura","Derivados da Cana-de-açucar","Derivados da Cana-de-açucar", BigDecimal.valueOf(180), origemIndustria));
        itemProducaoRepository.save(new ItemProducao("MELADO", "Melaço de cana","Melaço de cana","Derivados da Cana-de-açucar", BigDecimal.valueOf(210), origemIndustria));
        itemProducaoRepository.save(new ItemProducao("FARMAND", "Farinha de Mandioca","Farinha de Mandioca","Farinha de Mandioca", BigDecimal.valueOf(210), origemIndustria));
        itemProducaoRepository.save(new ItemProducao("GLDC", "Geleias e doces","Geleias e doces","Geléias e Doces", BigDecimal.valueOf(210), origemIndustria));


        leite = itemProducaoRepository.save(new ItemProducao("LEITE", "Leite tipo C","Leite tipo C","Bovinos - Leite", BigDecimal.valueOf(210), origemPecuaria));
        bezerroMagro = itemProducaoRepository.save(new ItemProducao("BZMGR", "Boi magro 6-8@","Queijos","Bovinos - Carne", BigDecimal.valueOf(210), origemPecuaria));
        boiMagro = itemProducaoRepository.save(new ItemProducao("BOIMGR", "Boi magro 11-14 @","Queijos","Bovinos - Carne", BigDecimal.valueOf(210), origemPecuaria));
        boiGordo = itemProducaoRepository.save(new ItemProducao("BOIGRD", "Boi Gordo > 16@","Queijos","Bovinos - Carne", BigDecimal.valueOf(210), origemPecuaria));
        itemProducaoRepository.save(new ItemProducao("VCDSC", "Descarte de vacas","Queijos","Bovinos - Carne", BigDecimal.valueOf(210), origemPecuaria));

        itemProducaoRepository.save(new ItemProducao("OVOS", "Ovos","Ovos","Ovos", BigDecimal.valueOf(210), origemPecuaria));
        itemProducaoRepository.save(new ItemProducao("AVI", "Avicultura Integrada - Carne","Avicultura Integrada - Carne","Avicultura Integrada - Carne", BigDecimal.valueOf(210), origemPecuaria));
        itemProducaoRepository.save(new ItemProducao("AVnI", "Avicultura não Integrada - Carne","Avicultura NÃO Integrada - Carne","Avicultura Integrada - Carne", BigDecimal.valueOf(210), origemPecuaria));

        itemProducaoRepository.save(new ItemProducao("SNI", "Suinocultura Integrada - Carne","Suinocultura Integrada - Carne","Suinocultura Integrada - Carne", BigDecimal.valueOf(210), origemPecuaria));
        itemProducaoRepository.save(new ItemProducao("SNnI", "Suinocultura não Integrada - Carne","Suinocultura NÃO Integrada - Carne","Suinocultura não Integrada - Carne", BigDecimal.valueOf(210), origemPecuaria));

        itemProducaoRepository.save(new ItemProducao("MLH", "Milho","Milho","Milho", BigDecimal.valueOf(210), origemAgro));
        itemProducaoRepository.save(new ItemProducao("FJ", "Feijão","Feijão","Feijão", BigDecimal.valueOf(210), origemAgro));
        itemProducaoRepository.save(new ItemProducao("ARRZ", "Arroz","Arroz","Arroz", BigDecimal.valueOf(210), origemAgro));
        itemProducaoRepository.save(new ItemProducao("TMT", "Tomate","Tomate","Tomate", BigDecimal.valueOf(210), origemAgro));
        itemProducaoRepository.save(new ItemProducao("OLRGR", "Produtos Olericolas em Geral","Produtos Olericolas em Geral","Produtos Olericolas em Geral", BigDecimal.valueOf(210), origemAgro));
        itemProducaoRepository.save(new ItemProducao("OLRGR", "Produtos Olericolas em Geral","Produtos Olericolas em Geral","Produtos Olericolas em Geral", BigDecimal.valueOf(210), origemAgro));

        //outras rendas a serem informadas
        itemProducaoRepository.save(new ItemProducao("OTRND", "Outras Atividades não Agropecuárias do Estabelecimento","Outras Atividades não Agropecuárias do Estabelecimento","Outras Atividades não Agropecuárias do Estabelecimento", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("RR", "Rendas não Rurais, Excluídos a Aposentadoria Rural e Outros Benefícios Sociais","Rendas não Rurais, Excluídos a Aposentadoria Rural e Outros Benefícios Sociais","Rendas não Rurais, Excluídos a Aposentadoria Rural e Outros Benefícios Sociais", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("ALGME", "Aluguel de Máquinas e Equipamentos","Aluguel de Máquinas e Equipamentos","Aluguel de Máquinas e Equipamentos", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("ARRD", "Arrendamento","Arrendamento","Arrendamento", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("VNDMO", "Venda da Mão-de-Obra da Unidade Familiar para Outros Estabelecimentos","Venda da Mão-de-Obra da Unidade Familiar para Outros Estabelecimentos","Venda da Mão-de-Obra da Unidade Familiar para Outros Estabelecimentos", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("EMPRR", "Empregos no Meio Rural","Empregos no Meio Rural","Empregos no Meio Rural", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("EMPURB", "Empregos no Meio Urbano","Empregos no Meio Urbano","Empregos no Meio Urbano", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("APSP", "Aposentadoria do serviço Público","Aposentadoria do serviço Público","Aposentadoria do serviço Público", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("PGRSC", "Programas Sociais","Programas Sociais","Programas Sociais", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("MC", "Meação","Meação","Meação", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("FRTCAM", "Frete de caminhão","Frete de caminhão","Frete de caminhão", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("FRTTRSP", "Transpote coletivo","Transpote coletivo","Transpote coletivo", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("EMPRCNPJ", "Renda de outras empresas","Renda de outras empresas","Renda de outras empresas", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("FRT", "Frete/taxi","Frete/taxi","Frete/taxi", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("CONST", "Serviços construção civil","Serviços construção civil","Serviços construção civil", BigDecimal.valueOf(1), origemOutras));
        itemProducaoRepository.save(new ItemProducao("CONST", "mão de obra diarista","mão de obra diarista","mão de obra diarista", BigDecimal.valueOf(1), origemOutras));

        //Itens para pesquisa de preços
        pricesItemRepository.save(new PricesItem("LTC","Leite tipo C","lt","litros","media diária de produção de leite", leite));
        pricesItemRepository.save(new PricesItem("BZMG","venda de Bezerros 5-8 @","@","arrobas","venda de bezerros", bezerroMagro));
        pricesItemRepository.save(new PricesItem("BZMG","venda de garrotes 9-15 @","@","arrobas","venda de garrotes", boiMagro));
        pricesItemRepository.save(new PricesItem("BZMG","venda de garrotes 16-21 @","@","arrobas","venda de boi gordobezerros", boiGordo));
    }
    private void LoadOrigemProducao(){

    }
    private void LoadPricesItem(){

    }
}
