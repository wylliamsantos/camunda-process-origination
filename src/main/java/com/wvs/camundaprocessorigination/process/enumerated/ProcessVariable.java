package com.wvs.camundaprocessorigination.process.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessVariable {

    REGISTER_DATA(1, "registerData"),
    PROCESS_ERROR(2, "processError"),
    PROCESS_ERROR_DESCRIPTION(3, "processErrorDescription"),
    NAME_PROCESS(4, "nameProcess"),
    DOCUMENT(5, "document");

    private Integer id;
    private String variableName;

}
