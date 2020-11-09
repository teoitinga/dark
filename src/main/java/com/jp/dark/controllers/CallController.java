package com.jp.dark.controllers;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.services.CallService;
import com.jp.dark.services.VisitaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.event.internal.CallbacksFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/chamadas")
@Slf4j
public class CallController {

    private CallService callService;

    private VisitaService visitaService;

    public CallController(
            CallService callService,
            VisitaService visitaService
    ) {
        this.callService = callService;
        this.visitaService = visitaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CallDTO save(@RequestBody @Valid CallDTO dto){

        return callService.save(dto);
    }
}
