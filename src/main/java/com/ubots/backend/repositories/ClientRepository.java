package com.ubots.backend.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ubots.backend.models.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
}
