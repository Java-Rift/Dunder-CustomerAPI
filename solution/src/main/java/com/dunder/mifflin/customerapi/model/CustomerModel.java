package com.dunder.mifflin.customerapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Customer API model object (Wrapper/abstraction for underlying entity object).
 *
 * @author Edom Mesfin
 * @since 2022
 */
@ApiModel(description = "Customer detail model object. Unique serial Id would be generated with proper time stamp.") //Optional Swagger annotation for API documentation.
@AllArgsConstructor
@NoArgsConstructor
public @Data class CustomerModel implements Serializable {

    @ApiModelProperty(name = "uuid", value = "Unique Customer identifier", dataType = "String", example = "b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143")
    private String uuid;
    @ApiModelProperty(name = "fName", value = "Customer First Name", dataType = "String", example = "John")
    private String fName;
    @ApiModelProperty(name = "lName", value = "Customer Last Name", dataType = "String", example = "Wick")
    private String lName;

}
