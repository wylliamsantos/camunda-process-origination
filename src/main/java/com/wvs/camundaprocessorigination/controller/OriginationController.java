package com.wvs.camundaprocessorigination.controller;

import com.wvs.camundaprocessorigination.input.OriginationInput;
import com.wvs.camundaprocessorigination.service.OriginationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/origination", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class OriginationController {

    private final OriginationService originationService;

    @PostMapping
    public ResponseEntity<Void> startOrigination(@RequestBody OriginationInput input) {
        log.info("New Request for body =[{}]", input);
        this.originationService.startOrigination(input);
        return ResponseEntity.ok().build();
    }

}
