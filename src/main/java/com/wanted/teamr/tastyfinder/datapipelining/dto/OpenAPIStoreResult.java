package com.wanted.teamr.tastyfinder.datapipelining.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class OpenAPIStoreResult {

    @JsonProperty("CODE")
    private String code;
    @JsonProperty("MESSAGE")
    private String message;

}
