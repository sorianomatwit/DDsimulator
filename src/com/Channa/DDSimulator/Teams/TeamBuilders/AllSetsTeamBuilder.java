package com.Channa.DDSimulator.Teams.TeamBuilders;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Channa.DDSimulator.Demigod;
import com.Channa.DDSimulator.Teams.Team;

public class AllSetsTeamBuilder extends ATeamBuilder {

  final int poolSize;
  public final int teamSize;
  public final long startIndex, endIndex;
  long setIndex;

  public AllSetsTeamBuilder(List<Demigod> babiesPool, int size) {
    super(babiesPool);

    poolSize = babiesPool.size();
    teamSize = size;
    endIndex = CalculateTotalSets(poolSize, size);
    startIndex = 0;
    setIndex = 0;
  }

  public AllSetsTeamBuilder(List<Demigod> babiesPool, int size, long startIndex, long endIndex) {
    super(babiesPool);

    poolSize = babiesPool.size();
    teamSize = size;
    this.endIndex = Math.min(CalculateTotalSets(poolSize, size), endIndex);
    this.startIndex = startIndex;
    setIndex = startIndex;

    System.out.printf("All sets from %d to %d...\n", startIndex, endIndex);
  }

  @Override
  public Team GenerateNextTeam(String name, int size) {
    Team team = GenerateCurrentTeam(name, size);

    ++setIndex;

    return team;
  }

  public Team GenerateCurrentTeam(String name, int size) {
    if (size != teamSize)
    throw new InvalidParameterException("team size does not match builder's size");

    List<Demigod> team = new ArrayList<>();


    int[] indices = new int[teamSize];

    long permHelper = setIndex;
    for (int ii = indices.length - 1; ii >= 0 && permHelper > 0; --ii) {
      indices[ii] = (int) (permHelper % poolSize);
      // System.out.printf("%d: %d, ", permHelper, indices[ii]);

      permHelper /= poolSize;
      // System.out.printf("%d\n", permHelper);
    }

    // System.out.printf("%d: %s\n", setIndex, Arrays.toString(indices));

    for (int index : indices) {
      team.add(new Demigod(babiesPool.get(index)));
    }

    // System.out.println(team);

    return new Team(name, team);
  }

  @Override
  public boolean HasNextTeam() {
    return setIndex < endIndex;
  }

  public static long CalculateTotalSets(long n, int r) {
    long numPerms = 1;

    for (int ii = 0; ii < r; ii++)
      numPerms *= n;

    return numPerms;
  }

  public long GetSetCount() {
    return endIndex - startIndex;
  }

  public void ResetBuilder() {
    setIndex = 0;
  }

}