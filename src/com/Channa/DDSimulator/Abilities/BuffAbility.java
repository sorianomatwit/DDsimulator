package com.Channa.DDSimulator.Abilities;
import com.Channa.DDSimulator.Demigod;
import com.Channa.DDSimulator.Simulator;
import com.Channa.DDSimulator.Abilities.AbilityEnums.*;

public class BuffAbility extends Ability{
	private int addDmg;
	private int addHp;
	public BuffAbility(TeamAffected targetTeam,TargetPosition target, AbilityTrigger trigger,int targetAmt, int addDmg, int addHp) {
		this.addDmg = addDmg;
		this.addHp = addHp;
		this.priority = 2;
		this.trigger = trigger;
		this.targetTeam = targetTeam;
		this.target = target;
		this.targetAmount = targetAmt;
	}
	
	@Override
	protected void Act(Simulator sim, Demigod demigod) {
		// TODO Auto-generated method stub
		if(demigod.atk + addDmg >= 1) demigod.atk += addDmg;
		
		if(demigod.hp + addHp >= 1) demigod.hp += addHp;
		//System.out.printf("buffed %s for %d/%d%n",demigod.getName(),addDmg,addHp);
	}

	@Override
	public String toString() {
		return "Buff" + super.toString();
	}
}
