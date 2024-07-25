package com.ubots.backend.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ClientAttendantIssueRecordDto(
    @NotNull UUID clientId,
    @NotNull UUID attendantId,
    @NotNull UUID issueId) {
}
