package com.ubots.backend.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ubots.backend.models.ClientAttendantIssueModel;

public interface ClientAttendantIssueRepository extends JpaRepository<ClientAttendantIssueModel, UUID> {
}
