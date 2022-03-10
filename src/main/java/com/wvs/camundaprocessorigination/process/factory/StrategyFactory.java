package com.wvs.camundaprocessorigination.process.factory;

import com.wvs.camundaprocessorigination.handler.ErrorHandler;
import com.wvs.camundaprocessorigination.process.enumerated.ProcessName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Component
public class StrategyFactory {

    private Map<ProcessName, ErrorHandler> strategies = new EnumMap<>(ProcessName.class);

    @Autowired
    public StrategyFactory(Set<ErrorHandler> strategySet) {
        createStrategy(strategySet);
    }

    public ErrorHandler findStrategy(ProcessName name) {
        return strategies.get(name);
    }

    private void createStrategy(Set<ErrorHandler> strategySet) {
        strategySet.forEach(strategy -> strategies.put(strategy.getProcessName(), strategy));
    }

}
