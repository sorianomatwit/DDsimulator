import AbilityEnums.*;

public class DmgAbility extends Ability{
	private int damageAmount = 1;
	
	public DmgAbility(Simulator sim,TeamAffected targetTeam,TargetPosition target, AbilityTrigger trigger, int targetAmt, int damageAmount) {
		this.damageAmount = damageAmount;
		this.priority = 3;
		this.sim = sim;
		this.trigger = trigger;
		this.targetTeam = targetTeam;
		this.target = target;
		this.targetAmount = targetAmt;
	}

	@Override
	protected void Act(Demigod demigod) {
		// TODO Auto-generated method stub
		sim.TakeDamage(demigod, damageAmount);
		//System.out.printf("%d dmg to %s%n", damageAmount,demigod.getName());
	}
	

}
