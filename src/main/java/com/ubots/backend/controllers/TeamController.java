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

import com.ubots.backend.dtos.TeamRecordDto;
import com.ubots.backend.models.TeamModel;
import com.ubots.backend.services.TeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("teams")
public class TeamController {
  private final TeamService teamService;

  public TeamController(TeamService teamService) {
    this.teamService = teamService;
  }

  @GetMapping
  public ResponseEntity<List<TeamModel>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(teamService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
    return teamService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Object> save(@Valid @RequestBody TeamRecordDto teamRecordDto) {
    return teamService.save(teamRecordDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id,
      @Valid @RequestBody TeamRecordDto teamRecordDto) {
    return teamService.update(id, teamRecordDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
    return teamService.deleteById(id);
  }
}
