import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import AbilityEnums.AbilityTrigger;
import AbilityEnums.TargetPosition;
import AbilityEnums.TeamAffected;

public class Main {
	
	public static <T> void reverseArray(int startIndex, int endIndex, ArrayList<T> bruh) {
		List<T> sub = bruh.subList(startIndex, endIndex);
		Collections.reverse(sub);
		for(int i = 0; i < sub.size();i++) {
			bruh.set(i, sub.get(i));
		}
	}
	
	public static void writeIntoExcel(ArrayList<Demigod> A, int num) throws FileNotFoundException, IOException {

		File file = new File("./Baby_Data"+num+".csv");
		file.createNewFile();
		FileWriter myWriter = new FileWriter(file.getPath());
		myWriter.write("RUN "+num+1);
		myWriter.write(String.format("%n"));
		try (FileOutputStream outputStream = new FileOutputStream(file.getPath())) {
			for (Demigod demigod : A) {
				myWriter.write(demigod.getName() + ",");
				myWriter.write(String.format("%.4f,", demigod.getWinRate()));
				myWriter.write(String.format("%.4f,", demigod.getLoseRate()));
				myWriter.write(String.format("%.4f,", demigod.getDrawRate()));
				myWriter.write(String.format("%.4f%n", demigod.deathRate()));
			}
		}
		myWriter.write(String.format("%n%n"));
		myWriter.close();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Simulator sim = new Simulator();
		// all my abilities
		Ability BuffPBA11 = new BuffAbility(sim, TeamAffected.Player, TargetPosition.Behind, AbilityTrigger.PreAttack,
				1, 1, 1);
		Ability BuffPRF11T3 = new BuffAbility(sim, TeamAffected.Player, TargetPosition.Random, AbilityTrigger.PostFaint,
				3, 1, 1);
		Ability BuffPBF02T2 = new BuffAbility(sim, TeamAffected.Player, TargetPosition.Behind, AbilityTrigger.PostFaint,
				2, 0, 2);
		Ability BuffPBLM_13 = new BuffAbility(sim, TeamAffected.Player, TargetPosition.BackOfLineup,
				AbilityTrigger.PreMatch, 1, -1, 3);
		Ability BuffPSA21 = new BuffAbility(sim, TeamAffected.Player, TargetPosition.Self, AbilityTrigger.PreAttack, 1,
				2, 1);
		Ability DamageEBLM1 = new DmgAbility(sim, TeamAffected.Enemy, TargetPosition.BackOfLineup,
				AbilityTrigger.PreMatch, 1, 1);
		Ability DamageEHHF3 = new DmgAbility(sim, TeamAffected.Enemy, TargetPosition.HighestHealth,
				AbilityTrigger.PostFaint, 1, 3);
		Ability DamageEHAM2 = new DmgAbility(sim, TeamAffected.Enemy, TargetPosition.HighestAttack,
				AbilityTrigger.PreMatch, 1, 2);
		Ability DamageELHM1 = new DmgAbility(sim, TeamAffected.Enemy, TargetPosition.LowestHealth,
				AbilityTrigger.PreMatch, 1, 1);
		Ability DamageERD1T2 = new DmgAbility(sim, TeamAffected.Enemy, TargetPosition.Random, AbilityTrigger.OnDamaged,
				2, 1);
		Ability DamageERM1 = new DmgAbility(sim, TeamAffected.Enemy, TargetPosition.Random, AbilityTrigger.PreMatch, 2,
				1);
		Ability SummonCerberus = new SummonAbility(sim, new Demigod("Cerberus", 2, 2));
		Ability SummonMedusa = new SummonAbility(sim, new Demigod("Medusa", 1, 4));
		System.out.println("created all abilities");

		ArrayList<Demigod> allBabies = new ArrayList<>();
		allBabies.add(new Demigod("Aphrodite", 1, 1, BuffPSA21));// 0
		allBabies.add(new Demigod("Apollo", 2, 5, DamageELHM1));// 1
		allBabies.add(new Demigod("Ares", 5, 2, DamageEBLM1));// 2
		allBabies.add(new Demigod("Artemis", 3, 2, BuffPBF02T2));// 3
		allBabies.add(new Demigod("Athena", 3, 3, SummonMedusa));// 4
		allBabies.add(new Demigod("Demeter", 2, 3, BuffPRF11T3));// 5
		allBabies.add(new Demigod("Dionysus", 3, 2, DamageEHHF3));// 6
		allBabies.add(new Demigod("Hades", 3, 2, SummonCerberus));// 7
		allBabies.add(new Demigod("Hephaestus", 2, 4, BuffPBLM_13));// 8
		allBabies.add(new Demigod("Hera", 2, 1, DamageEHAM2));// 9
		allBabies.add(new Demigod("Hemes", 3, 1, DamageERM1));// 10
		allBabies.add(new Demigod("Poseidon", 1, 2, BuffPBA11));// 11
		allBabies.add(new Demigod("Zeus", 2, 2, DamageERD1T2));// 12
		System.out.println("created all babies");
		// generate teams
		sim.allBabies.addAll(allBabies);
		sim.allBabies.remove(3);
		int[] team = { 6, 6,3,11,6 };
		ArrayList<Demigod> a = new ArrayList<>();
		for (int i : team) {
			a.add(allBabies.get(i));
		}
		sim.EallBabies.addAll(a);
		Team m = new Team("Animal",a);
		
		// sim.scalePlayerby(300);
		System.out.println("added all babies");
		System.out.println("start simulation");
		int numOfSims = 1;
		for (int i = 0; i < numOfSims; i++) {
			System.out.println(sim.simulate(10000, 1, 5));

			try {
				writeIntoExcel(sim.allBabies,i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
