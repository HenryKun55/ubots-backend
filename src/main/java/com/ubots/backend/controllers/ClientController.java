package com.ubots.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubots.backend.dtos.ClientRecordDto;
import com.ubots.backend.models.ClientModel;
import com.ubots.backend.services.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("clients")
public class ClientController {
  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  public ResponseEntity<List<ClientModel>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(clientService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
    return clientService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Object> save(@Valid @RequestBody ClientRecordDto clientRecordDto) {
    return clientService.save(clientRecordDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id,
      @Valid @RequestBody ClientRecordDto clientRecordDto) {
      return clientService.update(id, clientRecordDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
    return clientService.deleteById(id);
  }
}
