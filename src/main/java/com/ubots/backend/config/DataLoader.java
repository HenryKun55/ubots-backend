package com.ubots.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ubots.backend.repositories.*;
import com.ubots.backend.models.*;

@Component
public class DataLoader implements CommandLineRunner {

  private final TeamRepository teamRepository;
  private final ClientRepository clientRepository;
  private final AttendantRepository attendantRepository;
  private final IssueRepository issueRepository;

  public DataLoader(TeamRepository teamRepository, ClientRepository clientRepository,
      AttendantRepository attendantRepository, IssueRepository issueRepository) {
    this.teamRepository = teamRepository;
    this.clientRepository = clientRepository;
    this.attendantRepository = attendantRepository;
    this.issueRepository = issueRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    try {
      TeamModel cards = new TeamModel();
      cards.setName("Cartões");
      TeamModel loans = new TeamModel();
      loans.setName("Empréstimo");
      TeamModel other = new TeamModel();
      other.setName("Outros Assuntos");
      TeamModel cardsTeam = teamRepository.save(cards);
      TeamModel loansTeam = teamRepository.save(loans);
      TeamModel othersTeam = teamRepository.save(other);

      AttendantModel attendant = new AttendantModel();
      attendant.setName("João");
      attendant.setTeam(cardsTeam);
      AttendantModel attendant2 = new AttendantModel();
      attendant2.setName("Maria");
      attendant2.setTeam(loansTeam);
      AttendantModel attendant3 = new AttendantModel();
      attendant3.setName("Paulo");
      attendant3.setTeam(othersTeam);
      attendantRepository.save(attendant);
      attendantRepository.save(attendant2);
      attendantRepository.save(attendant3);

      ClientModel client = new ClientModel();
      client.setName("Léo");
      ClientModel client2 = new ClientModel();
      client2.setName("Daiane");
      ClientModel client3 = new ClientModel();
      client3.setName("Mariana");
      clientRepository.save(client);
      clientRepository.save(client2);
      clientRepository.save(client3);

      IssueModel cardIssue = new IssueModel();
      cardIssue.setName("Problemas com cartão");
      cardIssue.setTeam(cardsTeam);
      IssueModel loanIssue = new IssueModel();
      loanIssue.setName("Contratação de empréstimo");
      loanIssue.setTeam(loansTeam);
      IssueModel otherIssue = new IssueModel();
      otherIssue.setName("Perdi a senha");
      otherIssue.setTeam(othersTeam);
      issueRepository.save(cardIssue);
      issueRepository.save(loanIssue);
      issueRepository.save(otherIssue);

    } catch (Exception e) {
    }
  }
}
