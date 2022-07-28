package com.Channa.DDSimulator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.SortedSet;

import com.Channa.DDSimulator.Abilities.Ability;
import com.Channa.DDSimulator.Abilities.SummonAbility;
import com.Channa.DDSimulator.Abilities.AbilityEnums.AbilityTrigger;
import com.Channa.DDSimulator.Teams.Team;
import com.Channa.DDSimulator.Teams.TeamBuilders.ATeamBuilder;
import com.Channa.DDSimulator.Teams.TeamBuilders.AllSetsTeamBuilder;
import com.Channa.DDSimulator.Teams.TeamBuilders.ConstantTeamBuilder;

public class Simulator {
	public HashMap<Integer,Demigod> allBabies;
	public Team team1, team2;
	public boolean isBeingScaled = true;

	public Simulator(List<Demigod> allBabies) {
		this.allBabies = new HashMap<>();
		for (Demigod demigod : allBabies) {
			int hashCode = demigod.hashCode();
			// System.out.printf("%d %s\n", hashCode, demigod.getName());
			this.allBabies.put(hashCode, demigod);
		}
	}

	public void clean(List<Demigod> team, boolean reportDeath) {
		ListIterator<Demigod> iter = team.listIterator();
		while (iter.hasNext()) {
			Demigod demigod = iter.next();
			if (demigod.isDead) {
				iter.remove();

				if (reportDeath) {
					Demigod baby = findBaby(demigod);
					if (baby != null) baby.deathCount++;
				}
			}
		}
	}

	public void fight(Demigod ply1, Demigod ply2) {
		boolean playerHasPreAttack = false;
		if (ply1.getAbility() != null)
			playerHasPreAttack = ply1.getAbility().trigger == AbilityTrigger.PreAttack;
		boolean enemyHasPreAttack = false;
		if (ply2.getAbility() != null)
			enemyHasPreAttack = ply2.getAbility().trigger == AbilityTrigger.PreAttack;
		// check if has preAttack ability
		if (playerHasPreAttack && enemyHasPreAttack) {
			// check priority
			if (ply1.getAbility().ComparePriority(ply2.getAbility())) {
				this.TriggerAbility(ply1);
				this.TriggerAbility(ply2);
			} else {
				this.TriggerAbility(ply2);
				this.TriggerAbility(ply1);
			}
		} else {
			if (playerHasPreAttack) {
				this.TriggerAbility(ply1);
			}
			if (enemyHasPreAttack) {
				this.TriggerAbility(ply2);
			}
		}

		if (!ply1.isDead && !ply2.isDead) {
			// System.out.printf("%s for %d vs %s for %d%n", ply1, ply1.atk, ply2,
			// ply2.atk);
			TakeDamage(ply2, ply1.atk);
			TakeDamage(ply1, ply2.atk);
//			if(!ply1.isDead && ply2.isDead) ply1.Won();
//			if(ply1.isDead && !ply2.isDead) ply1.Lost();
//			ply1.gamePlayed();

		}
	}

	public void preMatchabils() {
		ArrayList<Demigod> all = new ArrayList<>();
		all.addAll(team1.myTeam);
		all.addAll(team2.myTeam);
		for (Demigod demigod : all) {
			if (demigod.getAbility() != null) {
				if (demigod.getAbility().trigger == AbilityTrigger.PreMatch) {
					this.TriggerAbility(demigod);
				}
			}
		}
	}

	public void TriggerAbility(Demigod demigod) {
		// TODO Auto-generated method stub
		List<Demigod> myTeam = team1.myTeam.contains(demigod) ? team1.myTeam : team2.myTeam;
		List<Demigod> enemyteam = team1.myTeam.contains(demigod) ? team2.myTeam : team1.myTeam;
		// System.out.println(enemyteam);
		demigod.getAbility().Perform(this, demigod, myTeam, enemyteam);
		if (demigod.getAbility() instanceof SummonAbility) {
			if (team1.myTeam.contains(demigod)) {
				for (Demigod unit : team1.myTeam) {
					if (unit.getAbility() != null) {
						if (unit.getAbility().trigger == AbilityTrigger.OnSummon) {
							unit.getAbility().Perform(this, demigod, myTeam, enemyteam);
						}
					}
				}
			} else {
				for (Demigod unit : team2.myTeam) {
					if (unit.getAbility() != null) {
						if (unit.getAbility().trigger == AbilityTrigger.OnSummon) {
							unit.getAbility().Perform(this, demigod, myTeam, enemyteam);
						}
					}
				}
			}
		}
	}

	private Demigod findBaby(Demigod demigod) {
		int hashCode = demigod.hashCode();
		Demigod baby = allBabies.get(demigod.hashCode());

		// System.out.printf("%d %s\n", hashCode, demigod.getName());

		return baby;
	}

	public void aRound() {
		for (Demigod demigod : team1.myTeam) {
			Demigod baby = findBaby(demigod);
			if (baby != null) baby.gamePlayed();
		}
	}

	public void TakeDamage(Demigod demigod, int damageAmount) {

		demigod.hp -= damageAmount;
		Ability ability = demigod.getAbility();

		if (demigod.hp <= 0) {
			demigod.died();

			if (ability != null && ability.trigger == AbilityTrigger.PostFaint) {
				TriggerAbility(demigod);
			}

		} else {
			if (ability != null && ability.trigger == AbilityTrigger.OnDamaged) {
				TriggerAbility(demigod);
			}
		}
		// System.out.println(demigod + " " + demigod.isDead);
	}

	public void scalePlayerby(int a) {
		double percent = (.01 * a) + 1;
		for (Demigod demigod : team1.myTeam) {
			demigod.atk *= percent;
			demigod.hp *= percent;
			demigod.atk = (int) Math.ceil(demigod.atk);
			demigod.hp = (int) Math.ceil(demigod.hp);
		}
	}

	public void printTeamStats(List<Demigod> team) {
		String s = "BABY STATS%n";
		for (Demigod demigod : team) {
			s += demigod.stats() + "%n";
		}
		System.out.printf(s);
	}

	public void teamLost(List<Demigod> team) {
		for (Demigod demigod : team) {
			Demigod baby = findBaby(demigod);
			if (baby != null) baby.Lost();
		}
	}

	public void teamWon(List<Demigod> team) {
		for (Demigod demigod : team) {
			Demigod baby = findBaby(demigod);
			if (baby != null) baby.Won();
		}
	}

	/// NOT YET IMPLEMENTED DO NOT USE!!

	// public String simulate(int rounds, Team team) {
	// 	theteam = team;
	// 	String s = "";

	// 	for (int i = 0; i < rounds; i++) {
	// 		System.out.println(i);
	// 		generateTeam(team);
	// 		ArrayList<Demigod> copy1 = new ArrayList<>();
	// 		copy1.addAll(team1);
	// 		// System.out.println("running...");
	// 		aRound();
	// 		theteam.amtofGames++;
	// 		// System.out.printf("%s vs %s%n", team1, team2);
	// 		preMatchabils();
	// 		clean(team1);
	// 		clean(team2);
	// 		while (!team1.isEmpty() && !team2.isEmpty()) {
	// 			// System.out.println("running...");

	// 			fight(team1.get(0), team2.get(0));

	// 			clean(team1);
	// 			clean(team2);
	// 		}
	// 		if (team1.isEmpty() && !team2.isEmpty()) {
	// 			this.teamLost(copy1);
	// 			theteam.lose++;
	// 			// System.out.println("lost");
	// 		} else if (!team1.isEmpty() && team2.isEmpty()) {
	// 			this.teamWon(copy1);
	// 			theteam.wins++;
	// 			// System.out.println("win");
	// 		} // else System.out.println("draw");
	// 			// consildateData();
	// 	}
	// 	printTeamStats(allBabies);

	// 	// s += String.format("For %d stats %s", rounds, allBabies);
	// 	return s;
	// }

	/**
	 * ]
	 * 
	 * @param rounds    - int amount of matches you would like to run
	 * @param generator - change where they pick the team form
	 * @param teamsize  - how large do you want the team sizes to be max should be 5
	 * @return - a string of the result of battle only record the player team not
	 *         the enemy team into allBabies
	 */
	public String simulate(int rounds, ATeamBuilder team1Builder, ATeamBuilder team2Builder, int teamsize) {
		String s = "";

		for (int i = 0; i < rounds; i++) {
			if (!team1Builder.HasNextTeam() || !team2Builder.HasNextTeam()) break;
			// System.out.println(i);
			team1 = team1Builder.GenerateNextTeam("team1", teamsize);
			team2 = team2Builder.GenerateNextTeam("team2", teamsize);

			List<Demigod> copy1 = new ArrayList<>(team1.myTeam);
			List<Demigod> copy2 = new ArrayList<>(team2.myTeam);

			// System.out.print(team1.myTeam);
			// System.out.print(" vs ");
			// System.out.println(team2.myTeam);
			
			// System.out.println("running...");
			aRound();
			// System.out.printf("%s vs %s%n", team1, team2.myTeam);
			// System.out.printf("%s vs %s\n", team1.myTeam.size(), team2.myTeam.size());
			preMatchabils();
			clean(team1.myTeam, true);
			clean(team2.myTeam, false);
			// System.out.printf("%s vs %s\n", team1.myTeam.size(), team2.myTeam.size());
			while (!team1.myTeam.isEmpty() && !team2.myTeam.isEmpty()) {
				// System.out.println("running...");

				fight(team1.myTeam.get(0), team2.myTeam.get(0));

				clean(team1.myTeam, true);
				clean(team2.myTeam, false);
				// System.out.printf("%s vs %s\n", team1.myTeam.size(), team2.myTeam.size());
			}
			if (team1.myTeam.isEmpty() && !team2.myTeam.isEmpty()) {
				this.teamLost(copy1);
				// System.out.println("lost");
			} else if (!team1.myTeam.isEmpty() && team2.myTeam.isEmpty()) {
				this.teamWon(copy1);
				// System.out.println("win");
			} // else System.out.println("draw");
				// consildateData();
			// printTeamStats(new ArrayList<Demigod>(allBabies.values()));
		}
		printTeamStats(new ArrayList<Demigod>(allBabies.values()));

		// s += String.format("For %d stats %s", rounds, allBabies);
		return s;
	}
	
	public String simulate(ConstantTeamBuilder team1Builder, AllSetsTeamBuilder team2Builder) {
		String s = "";

		double wins = 0, losses = 0, gamesPlayed = 0;
		while (team1Builder.HasNextTeam() && team2Builder.HasNextTeam()) {
			// System.out.println(i);
			team1 = team1Builder.GenerateNextTeam("team1", team2Builder.teamSize);
			team2 = team2Builder.GenerateNextTeam("team2", team2Builder.teamSize);
			
			++gamesPlayed;

			List<Demigod> copy1 = new ArrayList<>(team1.myTeam);
			List<Demigod> copy2 = new ArrayList<>(team2.myTeam);

			// System.out.print(team1.myTeam);
			// System.out.print(" vs ");
			// System.out.println(team2.myTeam);
			
			// System.out.println("running...");
			aRound();
			// System.out.printf("%s vs %s%n", team1, team2.myTeam);
			// System.out.printf("%s vs %s\n", team1.myTeam.size(), team2.myTeam.size());
			preMatchabils();
			clean(team1.myTeam, true);
			clean(team2.myTeam, false);
			// System.out.printf("%s vs %s\n", team1.myTeam.size(), team2.myTeam.size());
			while (!team1.myTeam.isEmpty() && !team2.myTeam.isEmpty()) {
				// System.out.println("running...");

				fight(team1.myTeam.get(0), team2.myTeam.get(0));

				clean(team1.myTeam, true);
				clean(team2.myTeam, false);
				// System.out.printf("%s vs %s\n", team1.myTeam.size(), team2.myTeam.size());
			}
			if (team1.myTeam.isEmpty() && !team2.myTeam.isEmpty()) {
				System.out.println(copy2);
				this.teamLost(copy1);
				++losses;
				// System.out.println("lost");
			} else if (!team1.myTeam.isEmpty() && team2.myTeam.isEmpty()) {
				this.teamWon(copy1);
				++wins;
				// System.out.println("win");
			} // else System.out.println("draw");
				// consildateData();
			// printTeamStats(new ArrayList<Demigod>(allBabies.values()));
		}
		double draws = gamesPlayed - (wins + losses);

		// System.out.printf("WR: %.4f | LR: %.4f | DR: %.4f | GP: %d%n",
		// 	wins/gamesPlayed, losses/gamesPlayed, draws/gamesPlayed, (int)gamesPlayed + 1);
		// printTeamStats(new ArrayList<Demigod>(allBabies.values()));

		// s += String.format("For %d stats %s", rounds, allBabies);
		return s;
	}

	public Collection<Team> simulate(AllSetsTeamBuilder team1Builder, AllSetsTeamBuilder team2Builder) {
		Set<Team> demigodSet = new HashSet<>();

		System.out.printf("Running approx %d x %d simulations...\n", team1Builder.GetSetCount(), team2Builder.GetSetCount());

		int count = 0;

		while (team1Builder.HasNextTeam()) {
			team1 = team1Builder.GenerateNextTeam("team1", team1Builder.teamSize);

			List<Demigod> teamCopy = new ArrayList<>(team1.myTeam);
			Team currTeam = new Team(team1);

			while (team2Builder.HasNextTeam()) {
				++currTeam.amtofGames;

				team1 = team1Builder.GenerateCurrentTeam("team1", team1Builder.teamSize);
				team2 = team2Builder.GenerateNextTeam("team2", team2Builder.teamSize);
				// System.out.println(i);

				List<Demigod> copy1 = new ArrayList<>(team1.myTeam);
				List<Demigod> copy2 = new ArrayList<>(team2.myTeam);

				// System.out.print(team1.myTeam);
				// System.out.print(" vs ");
				// System.out.println(team2.myTeam);
				
				// System.out.println("running...");
				aRound();
				// System.out.printf("%s vs %s%n", team1, team2.myTeam);
				// System.out.printf("%s vs %s\n", team1.myTeam.size(), team2.myTeam.size());
				preMatchabils();
				clean(team1.myTeam, true);
				clean(team2.myTeam, false);
				// System.out.printf("%s vs %s\n", team1.myTeam.size(), team2.myTeam.size());
				while (!team1.myTeam.isEmpty() && !team2.myTeam.isEmpty()) {
					// System.out.println("running...");

					fight(team1.myTeam.get(0), team2.myTeam.get(0));

					clean(team1.myTeam, true);
					clean(team2.myTeam, false);
					// System.out.printf("%s vs %s\n", team1.myTeam.size(), team2.myTeam.size());
				}
				if (team1.myTeam.isEmpty() && !team2.myTeam.isEmpty()) {
					this.teamLost(copy1);
					++currTeam.lose;
					// System.out.println("lost");
				} else if (!team1.myTeam.isEmpty() && team2.myTeam.isEmpty()) {
					this.teamWon(copy1);
					++currTeam.wins;
					// System.out.println("win");
				} // else System.out.println("draw");
					// consildateData();
				// printTeamStats(new ArrayList<Demigod>(allBabies.values()));
			}
			
			team2Builder.ResetBuilder();

			// System.out.printf("%d > ", count++);
			// System.out.println(currTeam);

			demigodSet.add(currTeam);
		}

		// printTeamStats(new ArrayList<Demigod>(allBabies.values()));

		// s += String.format("For %d stats %s", rounds, allBabies);
		return demigodSet;
	}
}
