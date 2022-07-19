import AbilityEnums.*;

public class BuffAbility extends Ability{
	private int addDmg;
	private int addHp;
	public BuffAbility(Simulator sim,TeamAffected targetTeam,TargetPosition target, AbilityTrigger trigger,int targetAmt, int addDmg, int addHp) {
		this.addDmg = addDmg;
		this.addHp = addHp;
		this.priority = 2;
		this.sim = sim;
		this.trigger = trigger;
		this.targetTeam = targetTeam;
		this.target = target;
		this.targetAmount = targetAmt;
	}
	
	@Override
	protected void Act(Demigod demigod) {
		// TODO Auto-generated method stub
		demigod.atk += addDmg;
		demigod.hp += addHp;
		//System.out.printf("buffed %s for %d/%d%n",demigod.getName(),addDmg,addHp);
	}

}
