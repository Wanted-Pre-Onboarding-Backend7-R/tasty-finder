package com.wanted.teamr.tastyfinder.datapipelining.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class OpenAPIStoreHead {

    @JsonProperty("list_total_count")
    private long listTotalCount;
    @JsonProperty("RESULT")
    private OpenAPIStoreResult result;
    @JsonProperty("api_version")
    private String apiVersion;

}
