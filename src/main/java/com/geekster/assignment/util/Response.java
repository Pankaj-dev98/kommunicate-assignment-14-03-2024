package com.geekster.assignment.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    @JsonProperty(value = "message", index = 0)
    private String text;
    private LocalDateTime createdAt;
    private int responseCode;

}
