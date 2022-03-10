package com.wvs.camundaprocessorigination.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CamundaProcessConfig {

    @Value("${process.origination-process}")
    private String originationProcessDefinitionId;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    private ProcessDefinition originationProcess;

    @PostConstruct
    public void updateProcess() {
        this.originationProcess = searchProcess(originationProcessDefinitionId);
    }

    public synchronized ProcessInstantiationBuilder getOriginationProcess() {
        return runtimeService.createProcessInstanceById(originationProcess.getId());
    }

    private ProcessDefinition searchProcess(String processDefinitionId) {
        this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionId)
                .orderByProcessDefinitionVersion()
                .desc()
                .list()

        if (CollectionUtils.isEmpty(processes)) {
            log.info("Process definition not found -> {}", processDefinitionId);
            throw new RuntimeException(StringUtils.join("No Process definition found for processDefinitionId=", processDefinitionId));
        }

        return processes.stream().findFirst().orElseThrow(() -> new RuntimeException("Processes were not deployed in ProcessEngine"));
    }

}
