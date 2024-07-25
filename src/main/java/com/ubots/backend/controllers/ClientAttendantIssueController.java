package com.ubots.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubots.backend.models.ClientAttendantIssueModel;
import com.ubots.backend.dtos.ClientAttendantIssueRecordDto;
import com.ubots.backend.services.ClientAttendantIssueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("services")
public class ClientAttendantIssueController {

  private final ClientAttendantIssueService clientAttendantIssueService;

  public ClientAttendantIssueController(ClientAttendantIssueService clientAttendantIssueService) {
    this.clientAttendantIssueService = clientAttendantIssueService;
  }

  @GetMapping
  public ResponseEntity<List<ClientAttendantIssueModel>> findAll() {
    return clientAttendantIssueService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
    return clientAttendantIssueService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Object> save(@Valid @RequestBody ClientAttendantIssueRecordDto clientAttendantIssueRecordDto) {
    return clientAttendantIssueService.save(clientAttendantIssueRecordDto);
  }

  @PostMapping("/{id}/finish")
  public ResponseEntity<Object> finishService(@PathVariable(value = "id") UUID id) {
    return clientAttendantIssueService.finishService(id);
  }

}
