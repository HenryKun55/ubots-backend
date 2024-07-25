package com.ubots.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubots.backend.dtos.IssueRecordDto;
import com.ubots.backend.models.IssueModel;
import com.ubots.backend.repositories.IssueRepository;
import com.ubots.backend.repositories.TeamRepository;

@Service
public class IssueService {
  private final TeamRepository teamRepository;
  private final IssueRepository issueRepository;

  public IssueService(TeamRepository teamRepository, IssueRepository issueRepository) {
    this.teamRepository = teamRepository;
    this.issueRepository = issueRepository;
  }

  public ResponseEntity<Object> findById(UUID id) {
    Optional<IssueModel> issue = issueRepository.findById(id);
    if (issue.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Issue not found.");
    return ResponseEntity.status(HttpStatus.OK).body(issue.get());
  }

  public List<IssueModel> findAll() {
    return issueRepository.findAll();
  }

  @Transactional
  public ResponseEntity<Object> save(IssueRecordDto issueRecordDto) {
    try {
      IssueModel issue = new IssueModel();
      issue.setName(issueRecordDto.name());
      issue.setTeam(teamRepository.findById(issueRecordDto.teamId()).get());
      return ResponseEntity.status(HttpStatus.CREATED).body(issueRepository.save(issue));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Team not exists");
    }
  }

  @Transactional
  public ResponseEntity<Object> update(UUID id, IssueRecordDto issueRecordDto) {
    Optional<IssueModel> issueModel = issueRepository.findById(id);
    if (issueModel.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Issue not found.");

    var issue = issueModel.get();
    BeanUtils.copyProperties(issueRecordDto, issue);

    return ResponseEntity.status(HttpStatus.OK).body(issueRepository.save(issue));
  }

  @Transactional
  public ResponseEntity<Object> deleteById(UUID id) {
    Optional<IssueModel> issue = issueRepository.findById(id);
    if (!issue.isEmpty()) {
      issueRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK).body("Issue deleted.");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Issue not found.");
  }
}
