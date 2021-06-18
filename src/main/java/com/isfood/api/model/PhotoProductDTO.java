package com.isfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhotoProductDTO {
    private String description;
    private String nameFile;
    private String contentType;
    private Long size;
}
