package com.ubots.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ubots.backend.dtos.TeamRecordDto;
import com.ubots.backend.models.TeamModel;
import com.ubots.backend.repositories.TeamRepository;

import jakarta.transaction.Transactional;

@Service
public class TeamService {

  private final TeamRepository teamRepository;

  public TeamService(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  public ResponseEntity<Object> findById(UUID id) {
    Optional<TeamModel> attendant = teamRepository.findById(id);
    if (attendant.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");
    return ResponseEntity.status(HttpStatus.OK).body(attendant.get());
  }

  public List<TeamModel> findAll() {
    return teamRepository.findAll();
  }

  @Transactional
  public ResponseEntity<Object> save(TeamRecordDto teamRecordDto) {
    try {
      TeamModel team = new TeamModel();
      team.setName(teamRecordDto.name());
      return ResponseEntity.status(HttpStatus.CREATED).body(teamRepository.save(team));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Team name already exists");
    }
  }

  @Transactional
  public ResponseEntity<Object> update(UUID id, TeamRecordDto teamRecordDto) {
    Optional<TeamModel> teamModel = teamRepository.findById(id);
    if (teamModel.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");

    var team = teamModel.get();
    BeanUtils.copyProperties(teamRecordDto, team);
    return ResponseEntity.status(HttpStatus.OK).body(teamRepository.save(team));
  }

  @Transactional
  public ResponseEntity<Object> deleteById(UUID id) {
    Optional<TeamModel> team = teamRepository.findById(id);
    if (!team.isEmpty()) {
      teamRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK).body("Team deleted.");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");
  }
}
