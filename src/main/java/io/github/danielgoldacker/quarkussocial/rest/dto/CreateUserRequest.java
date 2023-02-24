package io.github.danielgoldacker.quarkussocial.rest.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotNull(message = "age is required")
    private Integer age;

}
