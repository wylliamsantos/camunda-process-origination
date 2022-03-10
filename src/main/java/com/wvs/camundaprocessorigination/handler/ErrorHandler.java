package com.wvs.camundaprocessorigination.handler;

import com.wvs.camundaprocessorigination.process.enumerated.ProcessName;
import org.camunda.bpm.engine.delegate.DelegateExecution;

public interface ErrorHandler {

    ProcessName getProcessName();

    void handle(final Throwable error, DelegateExecution execution);

}
