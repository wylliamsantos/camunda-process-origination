package com.wvs.camundaprocessorigination.input;

import com.fasterxml.jackson.databind.JsonSerializable;
import com.wvs.camundaprocessorigination.dto.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OriginationInput implements Serializable {

    private static final long serialVersionUID = 1215038354958980255L;

    @Valid
    @NotNull
    private ClientDTO client;

}
