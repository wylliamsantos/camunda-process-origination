package com.wvs.camundaprocessorigination.process.delegate;

import com.wvs.camundaprocessorigination.dto.ClientDTO;
import com.wvs.camundaprocessorigination.process.enumerated.ProcessName;
import com.wvs.camundaprocessorigination.process.enumerated.ProcessVariable;
import com.wvs.camundaprocessorigination.process.factory.StrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;

@Slf4j
public abstract class OriginationDelegate implements JavaDelegate, ProcessService {

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected StrategyFactory strategyFactory;

    @Override
    public void execute(DelegateExecution execution) {
        final StopWatch sw = StopWatch.createStarted();
        final String executionId = execution.getId();
        final String businessKey = execution.getBusinessKey();
        final String className = this.getClass().getSimpleName();
        try {
            log.info("Initializing {} for Execution [{}], BusinessKey=[{}]", className, executionId, businessKey);
            executeDelegate(execution);
        } catch (Exception e) {
            setVariable(execution, ProcessVariable.PROCESS_ERROR, Boolean.TRUE);
            setVariable(execution, ProcessVariable.PROCESS_ERROR_DESCRIPTION, e.getMessage());
            log.error("Error on {} execution for ID=[{}] | BusinessKey=[{}]", className, executionId, businessKey);
            if (e instanceof HttpStatusCodeException) {
                final HttpStatusCodeException hsce = (HttpStatusCodeException) e;
                log.error("Error on HTTP call | BusinessKey[{}] | statusCode=[{}] | responseBody=[{}]", businessKey, hsce.getRawStatusCode(), hsce.getResponseBodyAsString());
            }
            ProcessName processName = getVariable(execution, ProcessVariable.NAME_PROCESS);
            handle(e, execution, processName);
            throw e;
        } finally {
            sw.stop();
            log.info("Finishing {} to Execution={} | BusinessKey=[{}], Elapsed Time=[{}]", className, executionId, businessKey, sw);
        }
    }

    @Async
    private void handle(final Throwable error, final DelegateExecution execution, final ProcessName name) {
        log.info("Name: {}", name);
        log.info("Strategy: {}", strategyFactory.findStrategy(name));
        Optional.ofNullable(strategyFactory.findStrategy(name)).ifPresent(item -> item.handle(error, execution));
    }

    protected ClientDTO buildClient(DelegateExecution execution) {
        return getVariable(execution, ProcessVariable.REGISTER_DATA);
    }

    public abstract void executeDelegate(DelegateExecution execution);

}
