package com.ubots.backend.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ubots.backend.models.AttendantModel;

public interface AttendantRepository extends JpaRepository<AttendantModel, UUID> {
}
