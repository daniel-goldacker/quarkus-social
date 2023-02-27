package io.github.danielgoldacker.quarkussocial.rest.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreatePostRequest {
    @NotBlank(message = "text is required")
    private String text;
}
