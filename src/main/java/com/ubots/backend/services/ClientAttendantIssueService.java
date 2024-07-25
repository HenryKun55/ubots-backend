package com.ubots.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubots.backend.dtos.ClientAttendantIssueRecordDto;
import com.ubots.backend.models.AttendantModel;
import com.ubots.backend.models.ClientAttendantIssueModel;
import com.ubots.backend.models.ClientModel;
import com.ubots.backend.models.IssueModel;
import com.ubots.backend.repositories.AttendantRepository;
import com.ubots.backend.repositories.ClientAttendantIssueRepository;
import com.ubots.backend.repositories.ClientRepository;
import com.ubots.backend.repositories.IssueRepository;

@Service
public class ClientAttendantIssueService {

  private final ClientAttendantIssueRepository clientAttendantIssueRepository;
  private final ClientRepository clientRepository;
  private final AttendantRepository attendantRepository;
  private final IssueRepository issueRepository;

  public ClientAttendantIssueService(ClientAttendantIssueRepository clientAttendantIssueRepository,
      ClientRepository clientRepository, AttendantRepository attendantRepository, IssueRepository issueRepository) {
    this.clientAttendantIssueRepository = clientAttendantIssueRepository;
    this.clientRepository = clientRepository;
    this.attendantRepository = attendantRepository;
    this.issueRepository = issueRepository;
  }

  public ResponseEntity<Object> findById(UUID id) {
    Optional<ClientAttendantIssueModel> clientAttendantIssue = clientAttendantIssueRepository.findById(id);
    if (clientAttendantIssue.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found.");
    return ResponseEntity.status(HttpStatus.OK).body(clientAttendantIssue.get());
  }

  public ResponseEntity<List<ClientAttendantIssueModel>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(clientAttendantIssueRepository.findAll());
  }

  @Transactional
  public ResponseEntity<Object> save(ClientAttendantIssueRecordDto clientAttendantIssueRecordDto) {
    ClientAttendantIssueModel clientAttendantIssue = new ClientAttendantIssueModel();
    Optional<AttendantModel> attendant = attendantRepository.findById(clientAttendantIssueRecordDto.attendantId());
    if (attendant.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendant not found.");
    Optional<IssueModel> issue = issueRepository.findById(clientAttendantIssueRecordDto.issueId());
    if (issue.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Issue not found.");
    Optional<ClientModel> client = clientRepository.findById(clientAttendantIssueRecordDto.clientId());
    if (client.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");

    if (attendant.get().getTeam().getId() != issue.get().getTeam().getId())
      return ResponseEntity.status(HttpStatus.CONFLICT).body("The attendant does not match with issue team.");

    Integer countNotFinishedServices = 0;
    for (ClientAttendantIssueModel service : clientAttendantIssueRepository.findAll()) {
      if (service.getAttendant().getId() == attendant.get().getId() && !service.isFinished())
        countNotFinishedServices++;
    }

    if (countNotFinishedServices > 2)
      return ResponseEntity.status(HttpStatus.CONFLICT).body("The attendant is busy.");

    clientAttendantIssue.setClient(client.get());
    clientAttendantIssue.setAttendant(attendant.get());
    clientAttendantIssue.setIssue(issue.get());

    clientAttendantIssueRepository.save(clientAttendantIssue);
    return ResponseEntity.status(HttpStatus.CREATED).body("Service initiaded.");
  }

  @Transactional
  public ResponseEntity<Object> finishService(UUID id) {
    Optional<ClientAttendantIssueModel> clientAttendantIssue = clientAttendantIssueRepository.findById(id);
    if (clientAttendantIssue.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found.");

    if(clientAttendantIssue.get().isFinished())
      return ResponseEntity.status(HttpStatus.CONFLICT).body("This service is already finished.");

    var service  = clientAttendantIssue.get();
    service.setFinished(true);
    BeanUtils.copyProperties(clientAttendantIssue, service);

    clientAttendantIssueRepository.save(service);
    return ResponseEntity.status(HttpStatus.CREATED).body("Service finished.");
  }

}
