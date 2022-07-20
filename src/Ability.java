
import java.util.ArrayList;

import AbilityEnums.*;

public abstract class Ability {

	public Simulator sim;
	protected int priority;
	protected TeamAffected targetTeam;
	public TargetPosition target;
	public AbilityTrigger trigger;
	int targetAmount = 1; 

	public boolean ComparePriority(Ability other) {
		return other.priority >= priority;
	}

	protected ArrayList<Demigod> GetTargets(Demigod baby, ArrayList<Demigod> myTeam, ArrayList<Demigod> enemyTeam) {
		ArrayList<Demigod> targetBabies = new ArrayList<Demigod>();
		ArrayList<Demigod> curTeam = new ArrayList<Demigod>();
		// select which ArrayList we are suppose to collect affect players

		if (targetTeam == TeamAffected.Player || targetTeam == TeamAffected.Both)
			curTeam.addAll(myTeam);
		if (targetTeam == TeamAffected.Enemy || targetTeam == TeamAffected.Both)
			curTeam.addAll(enemyTeam);

		int index = myTeam.indexOf(baby);
		// take the target from the ArrayList and add them to the ArrayList of targets
		// wanting to be affected
		switch (target) {
		case Self:
			targetBabies.add(baby);
			break;
		case Random:// add random targets from the group selected
			for (var i = 0; i < targetAmount; i++) {
				int pos = Calc.RandomRange(0, curTeam.size());
				if (curTeam.size() != 0) {
					while (curTeam.get(pos).isDead) {
						curTeam.remove(pos);
						pos = Calc.RandomRange(0, curTeam.size());
						if (curTeam.size() <= 0)
							break;
					}
					if (curTeam.size() <= 0)
						break;
					targetBabies.add(curTeam.get(pos));
					curTeam.remove(pos);
				}
			}
			break;
		case Behind:
			for (int i = 1; i <= targetAmount; i++) {
				if (index + i < curTeam.size()) {
					while (curTeam.get(index + i).isDead) {
						i++;
						if (index + i >= curTeam.size())
							break;
					}
					if (index + i < curTeam.size())
						targetBabies.add(curTeam.get(index + i));
				}
			}
			break;
		case BackOfLineup: // back of the line of either team you call (not for Everthing)
			if (curTeam.size() > 0) {
				for (int i = curTeam.size() - 1; i > 0; i--) {
					if (!curTeam.get(i).isDead) {
						targetBabies.add(curTeam.get(i));
						break;
					}
				}
			}
			break;
		case FrontOfLineup: // front of the line of either team you call (not for Everything)
			if (curTeam.size() > 0) {
				for (int i = 0; i < curTeam.size(); i++) {
					if (!curTeam.get(i).isDead) {
						targetBabies.add(curTeam.get(i));
						break;
					}
				}
			}
			break;
		// these find the unit with the condition unit witht he highestAttack etc
		case HighestAttack:// highest atk for team selected
			Demigod HighestDmg = curTeam.get(0);
			if (curTeam.size() > 0) {
				for (Demigod demigod : curTeam) {
//					if (!demigod.isDead && (HighestDmg.isDead || HighestDmg.atk < demigod.atk))
//						HighestDmg = demigod;
					if (HighestDmg.atk < demigod.atk && !demigod.isDead && !HighestDmg.isDead) {
						HighestDmg = demigod;
					} else if (HighestDmg.isDead) {
						int i = 0;
						while (i < curTeam.size() && curTeam.get(i).isDead) {
							HighestDmg = curTeam.get(i);
							i++;
							if(!HighestDmg.isDead) break;
						}
					}
				}
				targetBabies.add(HighestDmg);
			}
			break;
		case HighestHealth: // highest hp for team selected
			if (curTeam.size() > 0) {
				Demigod HighestHp = curTeam.get(0);
				for (Demigod demigod : curTeam) {
					if (HighestHp.hp < demigod.hp && !demigod.isDead && !HighestHp.isDead) {
						HighestHp = demigod;
					} else if (HighestHp.isDead) {
						int i = 0;
						while (i < curTeam.size() && curTeam.get(i).isDead) {
							HighestHp = curTeam.get(i);
							i++;
						}
					}
				}
				targetBabies.add(HighestHp);
			}
			break;
		case LowestAttack: // lowest attack for team select
			if (curTeam.size() > 0) {
				Demigod LowestDmg = curTeam.get(0);
				for (Demigod demigod : curTeam) {
					if (LowestDmg.atk > demigod.atk && !demigod.isDead && !LowestDmg.isDead) {
						LowestDmg = demigod;
					} else if (LowestDmg.isDead) {
						int i = 0;
						while (i < curTeam.size() && curTeam.get(i).isDead) {
							LowestDmg = curTeam.get(i);
							i++;
						}
					}
				}
				targetBabies.add(LowestDmg);
			}
			break;
		case LowestHealth:// lowest hp for team select
			if (curTeam.size() > 0) {
				Demigod LowestHp = curTeam.get(0);
				for (Demigod demigod : curTeam) {
					if (LowestHp.hp > demigod.hp && !demigod.isDead && !LowestHp.isDead) {
						LowestHp = demigod;
					} else if (LowestHp.isDead) {
						int i = 0;
						while (i < curTeam.size() && curTeam.get(i).isDead) {
							LowestHp = curTeam.get(i);
							i++;
						}
					}
				}
				targetBabies.add(LowestHp);
			}
			break;
		}
		return targetBabies;
	}

	public void Perform(Demigod baby, ArrayList<Demigod> myTeam, ArrayList<Demigod> enemyTeam) {
		ArrayList<Demigod> targetBabies = GetTargets(baby, myTeam, enemyTeam);
		
		for (Demigod demigod : targetBabies) {
			Act(demigod);
		}
	}

	protected abstract void Act(Demigod demigod);
}
