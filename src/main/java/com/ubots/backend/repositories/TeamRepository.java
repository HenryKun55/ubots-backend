package com.ubots.backend.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ubots.backend.models.TeamModel;

public interface TeamRepository extends JpaRepository<TeamModel, UUID> {
}
