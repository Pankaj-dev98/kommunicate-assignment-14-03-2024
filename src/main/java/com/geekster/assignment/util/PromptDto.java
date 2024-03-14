package com.geekster.assignment.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Validated
public class PromptDto {
    @Length(min = 5, max = 128, message = "Prompt must be between 5 and 128 characters long")
    @NotBlank(message = "Prompt must not be null and must contain at least one non-whitespace character")
    @JsonProperty("partial_text")
    private String prompt;
}
