package com.ubots.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubots.backend.dtos.AttendantRecordDto;
import com.ubots.backend.models.AttendantModel;
import com.ubots.backend.repositories.AttendantRepository;
import com.ubots.backend.repositories.TeamRepository;

@Service
public class AttendantService {
  private final TeamRepository teamRepository;
  private final AttendantRepository attendantRepository;

  public AttendantService(TeamRepository teamRepository, AttendantRepository attendantRepository) {
    this.teamRepository = teamRepository;
    this.attendantRepository = attendantRepository;
  }

  public ResponseEntity<Object> findById(UUID id) {
    Optional<AttendantModel> attendant = attendantRepository.findById(id);
    if (attendant.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendant not found.");
    return ResponseEntity.status(HttpStatus.OK).body(attendant.get());
  }

  public List<AttendantModel> findAll() {
    return attendantRepository.findAll();
  }

  @Transactional
  public ResponseEntity<Object> save(AttendantRecordDto attendantRecordDto) {
    try {
      AttendantModel attendant = new AttendantModel();
      attendant.setName(attendantRecordDto.name());
      attendant.setTeam(teamRepository.findById(attendantRecordDto.teamId()).get());
      return ResponseEntity.status(HttpStatus.CREATED).body(attendantRepository.save(attendant));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Team not exists.");
    }
  }

  @Transactional
  public ResponseEntity<Object> update(UUID id, AttendantRecordDto attendantRecordDto) {
    Optional<AttendantModel> attendantModel = attendantRepository.findById(id);
    if (attendantModel.isEmpty())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendant not found.");

    var attendant = attendantModel.get();
    BeanUtils.copyProperties(attendantRecordDto, attendant);
    return ResponseEntity.status(HttpStatus.CREATED).body(attendantRepository.save(attendant));
  }

  @Transactional
  public ResponseEntity<Object> deleteById(UUID id) {
    Optional<AttendantModel> attendant = attendantRepository.findById(id);
    if (!attendant.isEmpty()) {
      attendantRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK).body("Attendant deleted.");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendant not found.");
  }
}
