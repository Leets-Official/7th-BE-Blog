package com.example.leets7th.domain.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class TestResDTO {
    @Builder
    public record TestDTO(
            @JsonProperty("string_one") String string_one,
            @JsonProperty("string_two") String string_two
    ) {}
}
