package com.ubots.backend.models;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "clients_attendants_issues")
@Table(name = "clients_attendants_issues")
public class ClientAttendantIssueModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToOne
  @JoinColumn(name = "client_id")
  private ClientModel client;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToOne
  @JoinColumn(name = "attendant_id")
  private AttendantModel attendant;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToOne
  @JoinColumn(name = "issue_id")
  private IssueModel issue;

  @Column(nullable = false)
  private boolean isFinished = false;

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ClientModel getClient() {
    return client;
  }

  public void setClient(ClientModel client) {
    this.client = client;
  }

  public AttendantModel getAttendant() {
    return attendant;
  }

  public void setAttendant(AttendantModel attendant) {
    this.attendant = attendant;
  }

  public IssueModel getIssue() {
    return issue;
  }

  public void setIssue(IssueModel issue) {
    this.issue = issue;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public void setFinished(boolean isFinished) {
    this.isFinished = isFinished;
  }

}
