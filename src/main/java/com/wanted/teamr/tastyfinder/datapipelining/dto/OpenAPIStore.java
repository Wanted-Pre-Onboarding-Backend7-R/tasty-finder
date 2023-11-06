package com.wanted.teamr.tastyfinder.datapipelining.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class OpenAPIStore {

    @JsonProperty("head")
    private List<OpenAPIStoreHead> head = new ArrayList<>();
    @JsonProperty("row")
    private List<OpenAPIStoreRow> rows = new ArrayList<>();

}
