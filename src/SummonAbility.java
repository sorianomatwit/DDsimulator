import java.util.ArrayList;

import AbilityEnums.*;

public class SummonAbility extends Ability{
	
	private Demigod mySummon;
	public SummonAbility(Simulator sim, Demigod theSummon) {
		this.mySummon = theSummon;
		this.priority = 1;
		this.sim = sim;
		this.trigger = AbilityTrigger.PostFaint;
		this.targetTeam = TeamAffected.Player;
		this.target = TargetPosition.Self;
		//this.targetAmount = targetAmt;
	}

	@Override
	protected void Act(Demigod demigod) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void Perform(Demigod baby, ArrayList<Demigod> myTeam, ArrayList<Demigod> enemyTeam) {
		int index = myTeam.indexOf(baby);
		if(index == -1) index  = 0;
		myTeam.set(index, mySummon);
		//System.out.printf("summoned %s%n",mySummon.getName());
	}

}
