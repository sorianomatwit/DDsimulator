package com.Channa.DDSimulator.Teams.TeamBuilders;

import java.util.ArrayList;
import java.util.List;

import com.Channa.DDSimulator.Calc;
import com.Channa.DDSimulator.Demigod;
import com.Channa.DDSimulator.Teams.Team;

public class RandomTeamBuilder extends ATeamBuilder {
  
  public RandomTeamBuilder(List<Demigod> babiesPool) {
    super(babiesPool);
  }

  private Demigod RandomBaby() {
    int index = Calc.RandomRange(0, babiesPool.size());
    
    return new Demigod(babiesPool.get(index));
  }

  @Override
  public Team GenerateNextTeam(String name, int size) {
    ArrayList<Demigod> roster = new ArrayList<Demigod>();

    for (int ii = 0; ii < size; ii++) {
      roster.add(RandomBaby());
    }

    Team nextTeam = new Team(name, roster);
    return nextTeam;
  }

  @Override
  public boolean HasNextTeam() {
    return true;
  }
}
