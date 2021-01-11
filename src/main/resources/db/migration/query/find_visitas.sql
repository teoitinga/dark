SELECT `jp-data`.visita.local_do_atendimento, `jp-data`.persona.nome, `jp-data`.service_provided.descricao FROM `jp-data`.service_provided join `jp-data`.chamadas on `jp-data`.chamadas.service_provided_id = `jp-data`.service_provided.codigo
join `jp-data`.visita on `jp-data`.visita.codigo = `jp-data`.chamadas.call_id
join `jp-data`.visita_produtores on `jp-data`.visita_produtores.visita_codigo = `jp-data`.visita.codigo
join `jp-data`.persona on `jp-data`.persona.cpf = `jp-data`.visita_produtores.produtores_cpf
 ;