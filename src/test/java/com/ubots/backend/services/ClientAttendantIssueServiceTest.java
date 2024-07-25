package com.ubots.backend.services;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ubots.backend.repositories.AttendantRepository;
import com.ubots.backend.repositories.ClientAttendantIssueRepository;
import com.ubots.backend.repositories.ClientRepository;
import com.ubots.backend.repositories.IssueRepository;
import com.ubots.backend.dtos.ClientAttendantIssueRecordDto;
import com.ubots.backend.models.AttendantModel;
import com.ubots.backend.models.ClientAttendantIssueModel;
import com.ubots.backend.models.ClientModel;
import com.ubots.backend.models.IssueModel;
import com.ubots.backend.models.TeamModel;

@ExtendWith(MockitoExtension.class)
public class ClientAttendantIssueServiceTest {

  @InjectMocks
  private ClientAttendantIssueService clientAttendantIssueService;

  @Mock
  private ClientAttendantIssueRepository clientAttendantIssueRepository;

  @Mock
  private ClientRepository clientRepository;

  @Mock
  private AttendantRepository attendantRepository;

  @Mock
  private IssueRepository issueRepository;

  @Test
  public void testFindById_whenServiceExists_thenReturnService() throws Exception {
    UUID id = UUID.randomUUID();
    ClientAttendantIssueModel expectedService = new ClientAttendantIssueModel();
    expectedService.setId(id);

    when(clientAttendantIssueRepository.findById(id)).thenReturn(Optional.of(expectedService));

    ResponseEntity<Object> response = clientAttendantIssueService.findById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedService, response.getBody());
  }

  @Test
  public void testFindById_whenServiceDoesNotExist_thenReturnNotFound() throws Exception {
    UUID id = UUID.randomUUID();

    when(clientAttendantIssueRepository.findById(id)).thenReturn(Optional.empty());

    ResponseEntity<Object> response = clientAttendantIssueService.findById(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Service not found.", response.getBody());
  }

  @Test
  public void testSave_whenAttendantIssueDataIsValid_thenReturnCreated() throws Exception {
    UUID clientId = UUID.randomUUID();
    UUID attendantId = UUID.randomUUID();
    UUID issueId = UUID.randomUUID();

    TeamModel team = new TeamModel();
    team.setId(UUID.randomUUID());

    ClientModel client = new ClientModel();
    client.setId(clientId);

    AttendantModel attendant = new AttendantModel();
    attendant.setId(attendantId);
    attendant.setTeam(team);

    IssueModel issue = new IssueModel();
    issue.setId(issueId);
    issue.setTeam(attendant.getTeam());

    ClientAttendantIssueRecordDto dto = new ClientAttendantIssueRecordDto(clientId, attendantId, issueId);

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    when(attendantRepository.findById(attendantId)).thenReturn(Optional.of(attendant));
    when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

    ResponseEntity<Object> response = clientAttendantIssueService.save(dto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("Service initiaded.", response.getBody());
  }

  @Test
  public void testSave_whenAttendantIssueDataIsNotValid_thenReturnCreated() throws Exception {
    UUID clientId = UUID.randomUUID();
    UUID attendantId = UUID.randomUUID();
    UUID issueId = UUID.randomUUID();

    TeamModel teamCorrect = new TeamModel();
    teamCorrect.setId(UUID.randomUUID());

    TeamModel teamIncorrect = new TeamModel();
    teamIncorrect.setId(UUID.randomUUID());

    ClientModel client = new ClientModel();
    client.setId(clientId);

    AttendantModel attendant = new AttendantModel();
    attendant.setId(attendantId);
    attendant.setTeam(teamCorrect);

    IssueModel issue = new IssueModel();
    issue.setId(issueId);
    issue.setTeam(teamIncorrect);

    ClientAttendantIssueRecordDto dto = new ClientAttendantIssueRecordDto(clientId, attendantId, issueId);

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    when(attendantRepository.findById(attendantId)).thenReturn(Optional.of(attendant));
    when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

    ResponseEntity<Object> response = clientAttendantIssueService.save(dto);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("The attendant does not match with issue team.", response.getBody());
  }

}
