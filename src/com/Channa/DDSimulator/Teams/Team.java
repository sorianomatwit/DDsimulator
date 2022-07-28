package com.Channa.DDSimulator.Teams;
import java.util.ArrayList;
import java.util.List;

import com.Channa.DDSimulator.Demigod;

public class Team {
	///NOTE: NOT YET IOMPLEMENTED DO NOT USE
	public List<Demigod> myTeam;
	public double amtofGames;
	public double wins;
	public double lose;
	public String name;
	public Team(String name, List<Demigod> team) {
		myTeam = team;
		this.name = name;
	}
	public Team(Team team) {
		myTeam = new ArrayList<>(team.myTeam);
		this.name = team.name;
	}
	
	public double winRate(){
		return wins/this.amtofGames;
	}
	public double loseRate(){
		return lose/this.amtofGames;
	}
	public double drawRate(){
		return (this.amtofGames-lose-wins)/this.amtofGames;
	}
	
	@Override
	public String toString() {
		return String.format("%s: Wr:%.4f | Lr %.4f | Dr %.4f",name, this.winRate(),this.loseRate(),drawRate());
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Team)) return false;
		return this.hashCode() == ((Team)other).hashCode();
	}

	@Override
	public int hashCode() {
		int buffer = 32;
		int hash = 1;
		for (Demigod demigod : myTeam) {
			hash += demigod.ID;
			hash *= buffer;
		}
		return hash;
	}
}
