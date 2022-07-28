package com.Channa.DDSimulator.Teams.TeamBuilders;

import java.util.List;

import com.Channa.DDSimulator.Demigod;
import com.Channa.DDSimulator.Teams.Team;

public abstract class ATeamBuilder {
  protected List<Demigod> babiesPool;

  public abstract Team GenerateNextTeam(String name, int size);

  protected ATeamBuilder(List<Demigod> babiesPool) {
    this.babiesPool = babiesPool;
  }

  public abstract boolean HasNextTeam();
}
