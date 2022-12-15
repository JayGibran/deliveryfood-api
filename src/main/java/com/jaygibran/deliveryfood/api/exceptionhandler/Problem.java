package com.jaygibran.deliveryfood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problem")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;
    @ApiModelProperty(example = "https://deliveryfood.api.br/business-exception", position = 5)
    private String type;
    @ApiModelProperty(example = "Business exception", position = 10)
    private String title;
    @ApiModelProperty(example = "It doesn't exist any state with id: 5", position = 15)
    private String detail;
    @ApiModelProperty(example = "It doesn't exist any state with id: 5", position = 20)
    private String userMessage;
    private OffsetDateTime timeStamp;
    @ApiModelProperty(example = "Object or field list which generated the errors (optional)", position = 30)
    private List<Object> objects;

    @ApiModel("ProblemObject")
    @Builder
    @Getter
    public static class Object {
        @ApiModelProperty(example = "price")
        private String name;
        @ApiModelProperty(example = "Price is mandatory")
        private String userMessage;
    }

}
