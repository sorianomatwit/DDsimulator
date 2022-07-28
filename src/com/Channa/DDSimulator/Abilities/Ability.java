package com.Channa.DDSimulator.Abilities;

import java.util.ArrayList;
import java.util.List;

import com.Channa.DDSimulator.Calc;
import com.Channa.DDSimulator.Demigod;
import com.Channa.DDSimulator.Main;
import com.Channa.DDSimulator.Simulator;
import com.Channa.DDSimulator.Abilities.AbilityEnums.*;

public abstract class Ability {

	protected int priority;
	protected TeamAffected targetTeam;
	public TargetPosition target;
	public AbilityTrigger trigger;
	int targetAmount = 1; 

	public boolean ComparePriority(Ability other) {
		return other.priority >= priority;
	}

	protected List<Demigod> GetTargets(Demigod baby, List<Demigod> myTeam, List<Demigod> enemyTeam) {
		List<Demigod> targetBabies = new ArrayList<Demigod>();
		List<Demigod> curTeam = new ArrayList<Demigod>();
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
		case Summon:
            for(Demigod unit: curTeam)
            {
                if (unit.isSummon && !unit.isDead)
                {
                    targetBabies.add(unit);
                }
            }
            break;
		case Adjancent:
            Demigod right = null, left = null;
            Main.reverseArray(0, myTeam.size(), curTeam);
            int unitPos = curTeam.indexOf(baby);
            for (int i = unitPos - 1; i >= 0; i--)
                if (!curTeam.get(i).isDead) {
                    left = curTeam.get(i);
                    break;
                }
            for (int i = unitPos + 1; i < curTeam.size(); i++)
                if (!curTeam.get(i).isDead) {
                    right = curTeam.get(i);
                    break;
                }
            if (right != null) targetBabies.add(right);
            if (left != null) targetBabies.add(left);
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
				if(!HighestDmg.isDead) targetBabies.add(HighestDmg);
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
				if(!HighestHp.isDead) targetBabies.add(HighestHp);
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
				if(!LowestDmg.isDead) targetBabies.add(LowestDmg);
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
				if(!LowestHp.isDead) targetBabies.add(LowestHp);
			}
			break;
		case SecondInLineup:
			//targetBabies.Add(curTeam[1]);
			for (int i = 1; i <= targetAmount; i++)
			{
				if (i < curTeam.size())
				{
					while (curTeam.get(i).isDead)
					{
						i++;
						if (i >= curTeam.size()) break;
					}

					if (i < curTeam.size())
					{
						targetBabies.add(curTeam.get(i));
					}
				}
			}
			break;
		}
		return targetBabies;
	}

	public void Perform(Simulator sim, Demigod baby, List<Demigod> myTeam, List<Demigod> enemyTeam) {
		List<Demigod> targetBabies = GetTargets(baby, myTeam, enemyTeam);
		
		for (Demigod demigod : targetBabies) {
			Act(sim, demigod);
		}
	}

	protected abstract void Act(Simulator sim, Demigod demigod);

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		switch(targetTeam) {
			case Both:
				str.append("A");
				break;
			case Enemy:
				str.append("E");
				break;
			case Player:
				str.append("P");
				break;
			default:
				break;
		}
		
		switch(target) {
			case Adjancent:
				str.append("A");
				break;
			case BackOfLineup:
				str.append("BL");
				break;
			case Behind:
				str.append("B");
				break;
			case FrontOfLineup:
				str.append("FL");
				break;
			case HighestAttack:
				str.append("HA");
				break;
			case HighestHealth:
				str.append("HH");
				break;
			case LowestAttack:
				str.append("LA");
				break;
			case LowestHealth:
				str.append("LH");
				break;
			case Random:
				str.append("R");
				break;
			case SecondInLineup:
				str.append("SL");
				break;
			case Self:
				str.append("S");
				break;
			case Summon:
				str.append("U");
				break;
			default:
				break;
		}

		switch (trigger) {
			case OnDamaged:
				str.append("D");
				break;
			case OnSummon:
				str.append("S");
				break;
			case PostFaint:
				str.append("F");
				break;
			case PreAttack:
				str.append("A");
				break;
			case PreMatch:
				str.append("M");
				break;
			default:
				break;
		}

		return str.toString();
	}
}
