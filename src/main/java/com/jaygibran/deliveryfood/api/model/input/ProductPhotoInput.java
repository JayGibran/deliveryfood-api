package com.jaygibran.deliveryfood.api.model.input;

import com.jaygibran.deliveryfood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductPhotoInput {

    @NotNull
    @FileSize(max = "1MB")
    private MultipartFile file;

    @NotBlank
    private String description;
}
