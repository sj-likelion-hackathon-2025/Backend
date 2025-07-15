package org.kwakmunsu.flowmate.global.config;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;

@Builder
public record ExampleHolder(
        Example holder,
        String name,
        int code
) {
}