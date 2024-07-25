package com.ubots.backend.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "teams")
@Table(name = "teams")
public class TeamModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String name;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
  private Set<AttendantModel> attendants = new HashSet<>();

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
  private Set<IssueModel> issues = new HashSet<>();

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<AttendantModel> getAttendants() {
    return attendants;
  }

  public void setAttendants(Set<AttendantModel> attendants) {
    this.attendants = attendants;
  }
}
