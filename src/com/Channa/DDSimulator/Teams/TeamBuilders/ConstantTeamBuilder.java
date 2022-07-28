package com.Channa.DDSimulator.Teams.TeamBuilders;

import java.util.ArrayList;
import java.util.List;

import com.Channa.DDSimulator.Demigod;
import com.Channa.DDSimulator.Teams.Team;

public class ConstantTeamBuilder extends ATeamBuilder {

  public ConstantTeamBuilder(List<Demigod> team) {
    super(team);

  }

  @Override
  public Team GenerateNextTeam(String name, int size) {
    if (size > babiesPool.size()) throw new IndexOutOfBoundsException("ConstantTeamBuilder's team does not have enough babies");
    List<Demigod> team = new ArrayList<>();

    for (int i = 0; i < size; i++) {
      team.add(new Demigod(babiesPool.get(i)));
    }

    return new Team(name, team);
  }

  @Override
  public boolean HasNextTeam() {
    return true;
  }


}