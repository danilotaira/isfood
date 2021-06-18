package com.isfood.api.model.input;

import com.isfood.core.validation.FileSize;
import com.isfood.core.validation.ValidImage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PhotoProductInput {

    @ValidImage(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @FileSize(max="1MB")
    @NotNull
    private MultipartFile file;

    @NotBlank
    private String description;
}
