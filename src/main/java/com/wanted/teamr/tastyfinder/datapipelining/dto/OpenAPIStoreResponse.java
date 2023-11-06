package com.wanted.teamr.tastyfinder.datapipelining.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class OpenAPIStoreResponse {

    private Map<String, List<OpenAPIStore>> storeMap = new HashMap<>();
    @JsonProperty("RESULT")
    private OpenAPIStoreResult result;

    @JsonAnySetter
    public void put(String name, List<OpenAPIStore> value) {
        storeMap.put(name, value);
    }

    public List<OpenAPIStoreRow> getRows() {
        return storeMap.values().stream().toList().get(0).get(1).getRows();
    }

}
