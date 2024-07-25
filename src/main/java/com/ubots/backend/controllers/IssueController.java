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

import com.ubots.backend.dtos.IssueRecordDto;
import com.ubots.backend.models.IssueModel;
import com.ubots.backend.services.IssueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("issues")
public class IssueController {
  private final IssueService issueService;

  public IssueController(IssueService issueService) {
    this.issueService = issueService;
  }

  @GetMapping
  public ResponseEntity<List<IssueModel>> getAllIssues() {
    return ResponseEntity.status(HttpStatus.OK).body(issueService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
    return issueService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Object> save(@Valid @RequestBody IssueRecordDto issueRecordDto) {
    return issueService.save(issueRecordDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id,
      @Valid @RequestBody IssueRecordDto issueRecordDto) {
      return issueService.update(id, issueRecordDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
    return issueService.deleteById(id);
  }
}
