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

import com.ubots.backend.dtos.AttendantRecordDto;
import com.ubots.backend.models.AttendantModel;
import com.ubots.backend.services.AttendantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("attendants")
public class AttendantController {
  private final AttendantService attendantService;

  public AttendantController(AttendantService attendantService) {
    this.attendantService = attendantService;
  }

  @GetMapping
  public ResponseEntity<List<AttendantModel>> getAllAttendants() {
    return ResponseEntity.status(HttpStatus.OK).body(attendantService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
    return attendantService.findById(id);
  }

  @PostMapping
  public ResponseEntity<Object> save(@Valid @RequestBody AttendantRecordDto attendantRecordDto) {
    return attendantService.save(attendantRecordDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id,
      @Valid @RequestBody AttendantRecordDto attendantRecordDto) {
      return attendantService.update(id, attendantRecordDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
    return attendantService.deleteById(id);
  }
}
