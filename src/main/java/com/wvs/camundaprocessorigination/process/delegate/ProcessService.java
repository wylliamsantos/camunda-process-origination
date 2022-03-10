package com.wvs.camundaprocessorigination.process.delegate;

import com.wvs.camundaprocessorigination.process.enumerated.ProcessVariable;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import java.io.Serializable;

public interface ProcessService {

    default <T> T getVariable(DelegateExecution execution, ProcessVariable processVariable){
        if (execution == null) {
            throw new IllegalArgumentException("Execution must not be null");
        }
        return (T) execution.getVariable(processVariable.getVariableName());
    }

    default void setVariable(DelegateExecution execution, ProcessVariable variableName, Serializable variableValue) {
        if (execution == null) {
            throw new IllegalArgumentException("Execution must not be null");
        }
        execution.setVariable(variableName.getVariableName(), variableValue);
    }
}
