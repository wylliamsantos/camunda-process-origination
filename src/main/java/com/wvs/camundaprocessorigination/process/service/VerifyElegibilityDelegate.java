package com.wvs.camundaprocessorigination.process.service;

import com.wvs.camundaprocessorigination.dto.ClientDTO;
import com.wvs.camundaprocessorigination.process.delegate.OriginationDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class VerifyElegibilityDelegate extends OriginationDelegate {

    @Override
    public void executeDelegate(DelegateExecution execution) {
        final ClientDTO client = buildClient(execution);
        execution.setVariable("elegibility", isElegible(client));
    }

    private Boolean isElegible(ClientDTO client) {
        long age = ChronoUnit.YEARS.between(LocalDate.now(), client.getBirthDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        BigDecimal income = client.getIncome();

        return age > 25 && income.compareTo(new BigDecimal(2000)) >= 0;
    }
}
