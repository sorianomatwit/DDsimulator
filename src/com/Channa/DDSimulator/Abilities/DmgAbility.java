package com.Channa.DDSimulator.Abilities;
import com.Channa.DDSimulator.Demigod;
import com.Channa.DDSimulator.Simulator;
import com.Channa.DDSimulator.Abilities.AbilityEnums.*;

public class DmgAbility extends Ability{
	private int damageAmount = 1;
	
	public DmgAbility(TeamAffected targetTeam,TargetPosition target, AbilityTrigger trigger, int targetAmt, int damageAmount) {
		this.damageAmount = damageAmount;
		this.priority = 3;
		this.trigger = trigger;
		this.targetTeam = targetTeam;
		this.target = target;
		this.targetAmount = targetAmt;
	}

	@Override
	protected void Act(Simulator sim, Demigod demigod) {
		// TODO Auto-generated method stub
		sim.TakeDamage(demigod, damageAmount);
		//System.out.printf("%d dmg to %s%n", damageAmount,demigod.getName());
	}
	
	@Override
	public String toString() {
		return "Dmg" + super.toString();
	}

}
