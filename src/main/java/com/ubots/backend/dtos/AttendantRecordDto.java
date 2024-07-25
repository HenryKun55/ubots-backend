package com.ubots.backend.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AttendantRecordDto(@NotBlank @NotNull String name, UUID teamId) {
}
