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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "issues")
@Table(name = "issues")
public class IssueModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToOne
  @JoinColumn(name = "team_id")
  private TeamModel team;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
  private Set<ClientAttendantIssueModel> services = new HashSet<>();

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

  public TeamModel getTeam() {
    return team;
  }

  public void setTeam(TeamModel team) {
    this.team = team;
  }

  public Set<ClientAttendantIssueModel> getServices() {
    return services;
  }

  public void setServices(Set<ClientAttendantIssueModel> services) {
    this.services = services;
  }

}
