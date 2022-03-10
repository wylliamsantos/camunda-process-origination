package com.wvs.camundaprocessorigination.process.service;

import com.wvs.camundaprocessorigination.client.ClientClient;
import com.wvs.camundaprocessorigination.dto.ClientDTO;
import com.wvs.camundaprocessorigination.process.delegate.OriginationDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveClientRegisterDelegate extends OriginationDelegate {

    @Autowired
    private ClientClient clienteClient;

    @Override
    public void executeDelegate(DelegateExecution execution) {
        final ClientDTO client = buildClient(execution);
        this.clienteClient.saveClient(client);
    }
}
