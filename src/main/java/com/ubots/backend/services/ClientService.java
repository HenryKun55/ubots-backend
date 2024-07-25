package com.ubots.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubots.backend.dtos.ClientRecordDto;
import com.ubots.backend.models.ClientModel;
import com.ubots.backend.repositories.ClientRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientService {

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public ResponseEntity<Object> findById(UUID id) {
    Optional<ClientModel> attendant = clientRepository.findById(id);
    if (attendant.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
    return ResponseEntity.status(HttpStatus.OK).body(attendant.get());
  }

  public List<ClientModel> findAll() {
    return clientRepository.findAll();
  }

  @Transactional
  public ResponseEntity<Object> save(ClientRecordDto clientRecordDto) {
    try {
      ClientModel client = new ClientModel();
      client.setName(clientRecordDto.name());
      return ResponseEntity.status(HttpStatus.CREATED).body(clientRepository.save(client));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Client name already exists");
    }
  }

  @Transactional
  public ResponseEntity<Object> update(UUID id, ClientRecordDto clientRecordDto) {
    Optional<ClientModel> clientModel = clientRepository.findById(id);
    if (clientModel.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");

    var client = clientModel.get();
    BeanUtils.copyProperties(clientRecordDto, client);
    return ResponseEntity.status(HttpStatus.OK).body(clientRepository.save(client));
  }

  @Transactional
  public ResponseEntity<Object> deleteById(UUID id) {
    Optional<ClientModel> client = clientRepository.findById(id);
    if (!client.isEmpty()) {
      clientRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK).body("Client deleted.");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
  }
}
