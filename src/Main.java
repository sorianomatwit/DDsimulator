import java.util.ArrayList;

import AbilityEnums.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Simulator sim = new Simulator();
		//all my abilities
		Ability BuffPBA11 = new BuffAbility(
				sim, TeamAffected.Player, TargetPosition.Behind, AbilityTrigger.PreAttack, 1, 1, 1);
		Ability BuffPRF11T3 = new BuffAbility(
				sim, TeamAffected.Player, TargetPosition.Random, AbilityTrigger.PostFaint, 3, 1, 1);
		Ability BuffPBF02T2 = new BuffAbility(
				sim, TeamAffected.Player, TargetPosition.Behind, AbilityTrigger.PostFaint, 2, 0, 2);
		Ability BuffPBLM_13	= new BuffAbility(
				sim, TeamAffected.Player, TargetPosition.BackOfLineup,AbilityTrigger.PreMatch,1,-1,3);
		Ability BuffPSA21 = new BuffAbility(
				sim, TeamAffected.Player, TargetPosition.Self,AbilityTrigger.PreAttack,1,2,1);
		Ability DamageEBLM1 = new DmgAbility(
				sim,TeamAffected.Enemy, TargetPosition.BackOfLineup, AbilityTrigger.PreMatch,1,1);
		Ability DamageEHHF3 = new DmgAbility(
				sim,TeamAffected.Enemy, TargetPosition.HighestHealth, AbilityTrigger.PostFaint,1,3);
		Ability DamageEHAM2 = new DmgAbility(
				sim, TeamAffected.Enemy, TargetPosition.HighestAttack, AbilityTrigger.PreMatch,2,1);
		Ability DamageELHM1 = new DmgAbility(
				sim, TeamAffected.Enemy, TargetPosition.LowestHealth,AbilityTrigger.PreMatch,1,1);
		Ability DamageERD1T2 = new DmgAbility(
				sim, TeamAffected.Enemy, TargetPosition.Random, AbilityTrigger.OnDamaged,2,1);
		Ability DamageERM1 = new DmgAbility(
				sim, TeamAffected.Enemy, TargetPosition.Random, AbilityTrigger.PreMatch,2,1);
		Ability SummonCerberus = new SummonAblility(sim, new Demigod("Cerberus",2,2));
		Ability SummonMedusa = new SummonAblility(sim, new Demigod("Medusa",1,4));
		System.out.println("created all abilities");
		
		ArrayList<Demigod> allBabies = new ArrayList<>();
		allBabies.add(new Demigod("Aphrodite",1,1,BuffPSA21));
		allBabies.add(new Demigod("Apollo",2,5,DamageELHM1));
		allBabies.add(new Demigod("Ares",5,2,DamageEBLM1));
		allBabies.add(new Demigod("Artemis",3,2,BuffPBF02T2));
		allBabies.add(new Demigod("Athena",3,3,DamageELHM1));
		allBabies.add(new Demigod("Demeter",2,3,BuffPRF11T3));
		allBabies.add(new Demigod("Dionysus",3,2,DamageEBLM1));
		allBabies.add(new Demigod("Hades",2,1,SummonCerberus));
		allBabies.add(new Demigod("Hephaestus",2,4,BuffPBLM_13));
		allBabies.add(new Demigod("Hera",2,1,DamageEHAM2));
		allBabies.add(new Demigod("Hemes",3,1,DamageERM1));
		allBabies.add(new Demigod("Poseidon",1,2,BuffPBA11));
		allBabies.add(new Demigod("Zeus",2,2,DamageERD1T2));
		System.out.println("created all babies");
		//generate teams
		sim.allBabies.addAll(allBabies);
		sim.EallBabies.add(allBabies.get(0));
		sim.EallBabies.add(allBabies.get(2));
		//sim.scalePlayerby(300);
		System.out.println("added all babies");
		System.out.println("start simulation");
		int numOfSims = 1;
		for(int i = 0; i < numOfSims;i++) System.out.println(sim.simulate(1000));
	}

}
