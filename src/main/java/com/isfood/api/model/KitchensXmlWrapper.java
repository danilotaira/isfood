package com.isfood.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.isfood.domain.entity.Kitchen;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "kithens")
public class KitchensXmlWrapper {

    @NonNull
    @JsonProperty(value = "kithen")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Kitchen> kitchens;
}

