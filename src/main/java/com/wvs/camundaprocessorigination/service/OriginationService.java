package com.wvs.camundaprocessorigination.service;

import com.wvs.camundaprocessorigination.config.CamundaProcessConfig;
import com.wvs.camundaprocessorigination.input.OriginationInput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.wvs.camundaprocessorigination.process.enumerated.ProcessVariable.*;

@Service
@Slf4j
public class OriginationService {

    @Value("${process.businesskey-origination-prefix}")
    private String originationProcessBusinessKeyPrefix;

    @Autowired
    private CamundaProcessConfig camundaProcessConfig;

    public void startOrigination(OriginationInput input) {
        final StopWatch sw = StopWatch.createStarted();

        final String document = input.getClient().getDocument();

        final ProcessInstantiationBuilder processInstantiationBuilder = this.camundaProcessConfig.getOriginationProcess();

        processInstantiationBuilder.businessKey(StringUtils.join(originationProcessBusinessKeyPrefix, document));
        processInstantiationBuilder.setVariable(REGISTER_DATA.getVariableName(), input.getClient());
        processInstantiationBuilder.setVariable(PROCESS_ERROR.getVariableName(), Boolean.FALSE);
        processInstantiationBuilder.setVariable(DOCUMENT.getVariableName(), document);
        final ProcessInstance processInstance = processInstantiationBuilder.execute();
        sw.stop();
        log.info("Created process for ProcessInstanceId=[{}] | BusinessKey=[{}] | Elapsed time=[{}]", processInstance.getProcessInstanceId(), processInstance.getBusinessKey(), sw);

    }

}
