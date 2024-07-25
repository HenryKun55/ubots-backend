package com.ubots.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeamRecordDto(@NotBlank @NotNull String name) {
}
