import java.util.ArrayList;
import java.util.ListIterator;

import AbilityEnums.AbilityTrigger;

public class Simulator {
	public ArrayList<Demigod> team1 = new ArrayList<>();
	public ArrayList<Demigod> team2 = new ArrayList<>();
	public ArrayList<Demigod> allBabies = new ArrayList<>();
	public ArrayList<Demigod> EallBabies = new ArrayList<>();
	
	public boolean isBeingScaled = true;

	public void generateTeam(int a) {
		team1.clear();
		team2.clear();
		switch(a) {
			default:
				for (int i = 0; i < 5; i++) {
					team1.add(new Demigod(allBabies.get(Calc.RandomRange(0, allBabies.size()))));
					team2.add(new Demigod(allBabies.get(Calc.RandomRange(0, allBabies.size()))));
				}
			break;
			case 1:
				for (int i = 0; i < 5; i++) {
					team1.add(new Demigod(allBabies.get(Calc.RandomRange(0, allBabies.size()))));
				}
				for (int i = 0; i < 5; i++) {
					team2.add(new Demigod(EallBabies.get(Calc.RandomRange(0, EallBabies.size()))));
				}
			break;
			case 2:
				for (int i = 0; i < 5; i++) {
					team1.add(new Demigod(EallBabies.get(Calc.RandomRange(0, EallBabies.size()))));
					team2.add(new Demigod(EallBabies.get(Calc.RandomRange(0, EallBabies.size()))));
				}
				break;
			
		}
		
	}

	public void clean(ArrayList<Demigod> team) {
		ListIterator<Demigod> iter = team.listIterator();
		while (iter.hasNext()) {
			if (iter.next().isDead)
				iter.remove();
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
			//System.out.printf("%s for %d vs %s for %d%n", ply1, ply1.atk, ply2, ply2.atk);
			TakeDamage(ply2, ply1.atk);
			TakeDamage(ply1, ply2.atk);
//			if(!ply1.isDead && ply2.isDead) ply1.Won();
//			if(ply1.isDead && !ply2.isDead) ply1.Lost();
//			ply1.gamePlayed();

		}
	}

	public void preMatchabils() {
		ArrayList<Demigod> all = new ArrayList<>();
		all.addAll(team1);
		all.addAll(team2);
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
		ArrayList<Demigod> myTeam = team1.contains(demigod) ? team1 : team2;
		ArrayList<Demigod> enemyteam = team1.contains(demigod) ? team2 : team1;
		System.out.println(enemyteam);
		demigod.getAbility().Perform(demigod, myTeam, enemyteam);
	}

	public void aRound() {
		for (Demigod demigod : team1) {
			for (Demigod baby : allBabies) {
				if (baby.equals(demigod)) {
					baby.gamePlayed();
					break;
				}
			}
		}
	}

	public void TakeDamage(Demigod demigod, int damageAmount) {
		
		demigod.hp -= damageAmount;
		if (demigod.hp <= 0) {
			demigod.died();
			for (Demigod baby : allBabies) {
				if (team1.contains(demigod) && baby.equals(demigod)) {
					baby.deathCount++;
					break;
				}
			}
			if (demigod.getAbility() != null) {
				if (demigod.getAbility().trigger == AbilityTrigger.PostFaint) {
					TriggerAbility(demigod);

				}
			}
		} else {
			if (demigod.getAbility() != null) {
				if (demigod.getAbility().trigger == AbilityTrigger.OnDamaged) {
					TriggerAbility(demigod);
				}
			}
		}
		System.out.println(demigod + " " + demigod.isDead);
	}

	public void scalePlayerby(int a) {
		double percent = (.01 * a) + 1;
		for (Demigod demigod : team1) {
			demigod.atk *= percent;
			demigod.hp *= percent;
			demigod.atk = (int) Math.ceil(demigod.atk);
			demigod.hp = (int) Math.ceil(demigod.hp);
		}
	}

	public void printTeamStats(ArrayList<Demigod> team) {
		String s = "BABY STATS%n";
		for (Demigod demigod : team) {
			s += demigod.stats() + "%n";
		}
		System.out.printf(s);
	}

	public void teamLost(ArrayList<Demigod> team) {
		for (Demigod demigod : team) {
			for (Demigod baby : allBabies) {
				if (baby.equals(demigod)) {
					baby.Lost();
					break;
				}
			}
		}
	}

	public void teamWon(ArrayList<Demigod> team) {
		for (Demigod demigod : team) {
			for (Demigod baby : allBabies) {
				if (baby.equals(demigod)) {
					baby.Won();
					break;
				}
			}
		}
	}

	public String simulate(int rounds) {
		String s = "";

		for (int i = 0; i < rounds; i++) {
			// System.out.println(i);
			generateTeam(2);
			ArrayList<Demigod> copy1 = new ArrayList<>();
			copy1.addAll(team1);
			System.out.println("running...");
			aRound();
			//System.out.printf("%s vs %s%n", team1, team2);
			preMatchabils();
			clean(team1);
			clean(team2);
			while (!team1.isEmpty() && !team2.isEmpty()) {
				//System.out.println("running...");
				
				fight(team1.get(0), team2.get(0));

				clean(team1);
				clean(team2);
			}
			if (team1.isEmpty() && !team2.isEmpty()) {
				this.teamLost(copy1);
				// System.out.println("lost");
			} else if (!team1.isEmpty() && team2.isEmpty()) {
				this.teamWon(copy1);
				// System.out.println("win");
			} // else System.out.println("draw");
				// consildateData();
		}
		printTeamStats(allBabies);

		// s += String.format("For %d stats %s", rounds, allBabies);
		return s;
	}
}
