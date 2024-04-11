package com.sparta.smern.pojos;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


public class InventoryObject {

    @JsonProperty("approved")
    private Integer approved;
    @JsonProperty("placed")
    private Integer placed;
    @JsonProperty("delivered")
    private Integer delivered;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("approved")
    public Integer getApproved() {
        return approved;
    }

    @JsonProperty("approved")
    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    @JsonProperty("placed")
    public Integer getPlaced() {
        return placed;
    }

    @JsonProperty("placed")
    public void setPlaced(Integer placed) {
        this.placed = placed;
    }

    @JsonProperty("delivered")
    public Integer getDelivered() {
        return delivered;
    }

    @JsonProperty("delivered")
    public void setDelivered(Integer delivered) {
        this.delivered = delivered;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
