package com.wvs.camundaprocessorigination.dto;

import com.wvs.camundaprocessorigination.enumeration.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDTO implements Serializable {

    private static final long serialVersionUID = -2635038349547762843L;

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotNull
    private Date birthDate;
    @NotNull
    private BigDecimal income;
    @NotNull
    private String document;
    @NotNull
    private GenderType gender;

}
