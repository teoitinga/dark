SELECT * FROM `jp-data`.service_provided
left join chamadas on chamadas.service_provided_id = service_provided.codigo
left join visita on visita.codigo = chamadas.call_id
left join visita_produtores on visita_produtores.visita_codigo = visita.codigo
left join persona on visita_produtores.produtores_cpf = persona.cpf
where persona.nome like('%karysten%')
group by service_provided.codigo

;