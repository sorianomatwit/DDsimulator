package com.Channa.DDSimulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.Channa.DDSimulator.Teams.Team;
import com.Channa.DDSimulator.Teams.TeamBuilders.ATeamBuilder;
import com.Channa.DDSimulator.Teams.TeamBuilders.AllSetsTeamBuilder;

public class SimulatorThread extends Thread {

  final int runId;

  List<Demigod> allBabies;
  final int size;
  final AllSetsTeamBuilder team1Builder, team2Builder;

  private Collection<Team> teams;

  private static int threadCount = 0;

  public SimulatorThread(Collection<Demigod> allBabies, AllSetsTeamBuilder team1, AllSetsTeamBuilder team2, int teamSize) {
    runId = threadCount++;

    this.allBabies = new ArrayList<>(allBabies);

    size = teamSize;

    team1Builder = team1;
    team2Builder = team2;
  }
	
  @Override
  public void run() {
    try {
      // Displaying the thread that is running
      System.out.printf("Thread %d is running...\n", Thread.currentThread().getId());
      
      Simulator sim = new Simulator(allBabies);

      teams = sim.simulate(team1Builder, team2Builder);
      
      System.out.printf("Thread %d is done!\n", Thread.currentThread().getId());
    }
    catch (Exception e) {
      // Throwing an exception
      e.printStackTrace();
    }
  }

  public Collection<Team> GetTeams() { return teams; }

}
