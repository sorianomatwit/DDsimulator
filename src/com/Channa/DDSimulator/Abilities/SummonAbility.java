package com.Channa.DDSimulator.Abilities;
import java.util.ArrayList;
import java.util.List;

import com.Channa.DDSimulator.Demigod;
import com.Channa.DDSimulator.Simulator;
import com.Channa.DDSimulator.Abilities.AbilityEnums.*;

public class SummonAbility extends Ability{
	
	private Demigod mySummon;
	public SummonAbility(Demigod theSummon, TeamAffected targetTeam, TargetPosition target) {
		this.mySummon = theSummon;
		this.priority = 1;
		this.trigger = AbilityTrigger.PostFaint;
		this.targetTeam = targetTeam;
		this.target = target;
		//this.targetAmount = targetAmt;
	}
	public SummonAbility(Demigod theSummon) {
		this(theSummon, TeamAffected.Player, TargetPosition.Self);
	}

	@Override
	protected void Act(Simulator sim, Demigod demigod) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Perform(Simulator sim, Demigod baby, List<Demigod> myTeam, List<Demigod> enemyTeam) {
		int index = myTeam.indexOf(baby);
		if(index == -1) index  = 0;
		myTeam.set(index, mySummon);
		//System.out.printf("summoned %s%n",mySummon.getName());
	}
	
	@Override
	public String GetName() {
		return "Summ" + super.toString();
	}

}
