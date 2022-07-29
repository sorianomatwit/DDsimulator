package com.Channa.DDSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.Channa.DDSimulator.Abilities.Ability;
import com.Channa.DDSimulator.Abilities.BuffAbility;
import com.Channa.DDSimulator.Abilities.DmgAbility;
import com.Channa.DDSimulator.Abilities.SummonAbility;
import com.Channa.DDSimulator.Abilities.AbilityEnums.AbilityTrigger;
import com.Channa.DDSimulator.Abilities.AbilityEnums.TargetPosition;
import com.Channa.DDSimulator.Abilities.AbilityEnums.TeamAffected;
import com.Channa.DDSimulator.Teams.Team;
import com.Channa.DDSimulator.Teams.TeamBuilders.ATeamBuilder;
import com.Channa.DDSimulator.Teams.TeamBuilders.AllSetsTeamBuilder;
import com.Channa.DDSimulator.Teams.TeamBuilders.ConstantTeamBuilder;
import com.Channa.DDSimulator.Teams.TeamBuilders.RandomTeamBuilder;

public class Main {
	private final static int Aphrodite = 0;
	private final static int Apollo = 1;
	private final static int Ares = 2;
	private final static int Artemis = 3;
	private final static int Athena = 4;
	private final static int Demeter = 5;
	private final static int Dionysus = 6;
	private final static int Hades = 7;
	private final static int Hephaestus = 8;
	private final static int Hera = 9;
	private final static int Hermes = 10;
	private final static int Poseidon = 11;
	private final static int Zeus = 12;
	private final static int Hesita = 13;

	public static <T> void reverseArray(int startIndex, int endIndex, List<T> bruh) {
		List<T> sub = bruh.subList(startIndex, endIndex);
		Collections.reverse(sub);
		for (int i = 0; i < sub.size(); i++) {
			bruh.set(i, sub.get(i));
		}
	}

	public static void writeIntoExcel(ArrayList<Demigod> A, int num) throws FileNotFoundException, IOException {

		File file = new File("./Baby_Data" + num + ".csv");
		file.createNewFile();
		FileWriter myWriter = new FileWriter(file.getPath());
		myWriter.write("RUN " + num + 1);
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

	public static void writeIntoExcel(Collection<Team> teams) throws FileNotFoundException, IOException {
		writeIntoExcel(teams, 0);
	}

	public static void writeIntoExcel(Collection<Team> teams, int runId) throws FileNotFoundException, IOException {
		File file = new File(String.format("./Team_Data%d.csv", runId));
		file.createNewFile();
		FileWriter myWriter = new FileWriter(file.getPath());
		myWriter.write("Team Comp, Win Rate, Lose Rate, Draw Rate\n");
		try (FileOutputStream outputStream = new FileOutputStream(file.getPath())) {
			for (Team team : teams) {
				myWriter.write(team.myTeam.toString().replace(",", " |") + ",");
				myWriter.write(String.format("%.4f,", team.winRate()));
				myWriter.write(String.format("%.4f,", team.loseRate()));
				myWriter.write(String.format("%.4f%n", team.drawRate()));
			}
		}
		myWriter.write(String.format("%n%n"));
		myWriter.close();
	}

	public static void main(String[] args) {

		// all Buff abilities
		Ability BuffAAM10 = new BuffAbility(TeamAffected.Both, TargetPosition.Adjancent, AbilityTrigger.PreMatch, 1, 1,
				0);
		Ability BuffAAM_10 = new BuffAbility(TeamAffected.Both, TargetPosition.Adjancent, AbilityTrigger.PreMatch, 1,
				-1, 0);
		Ability BuffEBLD_10 = new BuffAbility(TeamAffected.Enemy, TargetPosition.BackOfLineup, AbilityTrigger.OnDamaged,
				1, -1, 0);
		Ability BuffPBA11 = new BuffAbility(TeamAffected.Player, TargetPosition.Behind, AbilityTrigger.PreAttack, 1, 1,
				1);
		Ability BuffPBF02T2 = new BuffAbility(TeamAffected.Player, TargetPosition.Behind, AbilityTrigger.PostFaint, 2,
				0, 2);
		Ability BuffPBLM_13 = new BuffAbility(TeamAffected.Player, TargetPosition.BackOfLineup, AbilityTrigger.PreMatch,
				1, -1, 3);
		Ability BuffPLHF20 = new BuffAbility(TeamAffected.Player, TargetPosition.LowestHealth, AbilityTrigger.PostFaint,
				1, 2, 0);
		Ability BuffPRF11T3 = new BuffAbility(TeamAffected.Player, TargetPosition.Random, AbilityTrigger.PostFaint, 3,
				1, 1);
		Ability BuffPSA21 = new BuffAbility(TeamAffected.Player, TargetPosition.Self, AbilityTrigger.PreAttack, 1, 2,
				1);
		Ability BuffPSM21 = new BuffAbility(TeamAffected.Player, TargetPosition.Self, AbilityTrigger.PreMatch, 1, 2, 1);
		
		Ability BuffPRM01T1 = new BuffAbility(TeamAffected.Player, TargetPosition.Random, AbilityTrigger.PreMatch, 1, 0,
				1);
		Ability BuffPUS11 = new BuffAbility(TeamAffected.Player, TargetPosition.Summon, AbilityTrigger.OnSummon, 1, 1,
				1);
		Ability BuffEFLD1_1 = new BuffAbility(TeamAffected.Enemy, TargetPosition.FrontOfLineup,
				AbilityTrigger.OnDamaged, 1, 1, -1);
		Ability BuffAAM01 = new BuffAbility(TeamAffected.Both, TargetPosition.Adjancent, AbilityTrigger.PreMatch, 1, 0,
				1);
		Ability BuffEHAM_20 = new BuffAbility(TeamAffected.Enemy, TargetPosition.HighestAttack, AbilityTrigger.PreMatch,
				1, 0, -2);
		Ability BuffERF_20T1 = new BuffAbility(TeamAffected.Enemy, TargetPosition.Random, AbilityTrigger.PostFaint, 1,
				-2, 0);
		Ability BuffELHF_31 = new BuffAbility(TeamAffected.Enemy, TargetPosition.LowestHealth, AbilityTrigger.OnDamaged,
				1, -3, 1);
		Ability BuffERS_10T1 = new BuffAbility(TeamAffected.Enemy, TargetPosition.Random, AbilityTrigger.OnSummon, 1,
				-1, 0);
		Ability BuffAAM11 = new BuffAbility(TeamAffected.Both, TargetPosition.Adjancent, AbilityTrigger.PreMatch, 1, 1,
				1);
		Ability BuffPFLM12 = new BuffAbility(TeamAffected.Player, TargetPosition.FrontOfLineup, AbilityTrigger.PreMatch,
				1, 1, 2);
		Ability BuffPBF20 = new BuffAbility(TeamAffected.Player, TargetPosition.Behind, AbilityTrigger.PostFaint, 1, 2,
				0);

		// All Damage
		Ability DamageAAF2 = new DmgAbility(TeamAffected.Both, TargetPosition.Adjancent, AbilityTrigger.PostFaint, 1,
				2);
		Ability DamageEBLM1 = new DmgAbility(TeamAffected.Enemy, TargetPosition.BackOfLineup, AbilityTrigger.PreMatch,
				1, 1);
		Ability DamageEFLM2 = new DmgAbility(TeamAffected.Enemy, TargetPosition.FrontOfLineup, AbilityTrigger.PreMatch,
				1, 2);
		Ability DamageEHAF1 = new DmgAbility(TeamAffected.Enemy, TargetPosition.HighestAttack, AbilityTrigger.PostFaint,
				1, 1);
		Ability DamageEHAM2 = new DmgAbility(TeamAffected.Enemy, TargetPosition.HighestAttack, AbilityTrigger.PreMatch,
				1, 2);
		Ability DamageEHHF3 = new DmgAbility(TeamAffected.Enemy, TargetPosition.HighestHealth, AbilityTrigger.PostFaint,
				1, 3);
		Ability DamageELHM1 = new DmgAbility(TeamAffected.Enemy, TargetPosition.LowestHealth, AbilityTrigger.PreMatch,
				1, 1);
		Ability DamageERD1T2 = new DmgAbility(TeamAffected.Enemy, TargetPosition.Random, AbilityTrigger.OnDamaged, 2,
				1);
		Ability DamageERM1 = new DmgAbility(TeamAffected.Enemy, TargetPosition.Random, AbilityTrigger.PreMatch, 2, 1);

		Ability DamageERS1T1 = new DmgAbility(TeamAffected.Enemy, TargetPosition.Random, AbilityTrigger.OnSummon, 1, 1);

		Ability DamagePBM1 = new DmgAbility(TeamAffected.Player, TargetPosition.Behind, AbilityTrigger.PreMatch, 1, 1);

		Ability DamageEFLF2 = new DmgAbility(TeamAffected.Enemy, TargetPosition.FrontOfLineup, AbilityTrigger.PostFaint, 1, 2);
		
		Ability DamageAAF1 = new DmgAbility(TeamAffected.Both, TargetPosition.Adjancent, AbilityTrigger.PostFaint, 1, 1);
		
		Ability DamageAAM1 = new DmgAbility(TeamAffected.Enemy, TargetPosition.Behind, AbilityTrigger.PreMatch, 1, 1);
		
		Ability DamageESLA1 = new DmgAbility(TeamAffected.Enemy, TargetPosition.SecondInLineup, AbilityTrigger.PreAttack, 1, 1);
		
		Ability DamageARF1T4 = new DmgAbility(TeamAffected.Both, TargetPosition.Random, AbilityTrigger.PostFaint, 4, 1);

		// Summon Abils
		Ability SummonCerberus = new SummonAbility(new Demigod(-1, "Cerberus", 2, 2));
		
		Ability SummonMedusa = new SummonAbility(new Demigod(-2, "Medusa", 1, 4));
		
		Ability SummonChariot = new SummonAbility(new Demigod(-3, "Chariot", 3, 1));
		
		Ability SummonArachne = new SummonAbility(new Demigod(-4, "Arachne", 3, 3), TeamAffected.Enemy,TargetPosition.Random);
		
		Ability SummonCornucopia = new SummonAbility(new Demigod(-5, "Cornucopia", 1, 3));
		// System.out.println("created all abilities");
		// third set

		ArrayList<Demigod> allBabies = new ArrayList<>();
		allBabies.add(new Demigod(0, "Aphrodite", 1, 1, BuffPSA21));// 0
		allBabies.add(new Demigod(1, "Apollo", 2, 5, DamageELHM1));// 1
		allBabies.add(new Demigod(2, "Ares", 5, 2, DamageEBLM1));// 2
		allBabies.add(new Demigod(3, "Artemis", 3, 2, BuffPBF02T2));// 3
		allBabies.add(new Demigod(4, "Athena", 3, 3, SummonMedusa));// 4
		allBabies.add(new Demigod(5, "Demeter", 2, 3, BuffPRF11T3));// 5
		allBabies.add(new Demigod(6, "Dionysus", 3, 2, DamageEHHF3));// 6
		allBabies.add(new Demigod(7, "Hades", 3, 2, SummonCerberus));// 7
		allBabies.add(new Demigod(8, "Hephaestus", 2, 4, BuffPBLM_13));// 8
		allBabies.add(new Demigod(9, "Hera", 2, 1, DamageEHAM2));// 9
		allBabies.add(new Demigod(10, "Hemes", 3, 1, DamageERM1));// 10
		allBabies.add(new Demigod(11, "Poseidon", 1, 2, BuffPBA11));// 11
		allBabies.add(new Demigod(12, "Zeus", 2, 2, DamageERD1T2));// 12
		allBabies.add(new Demigod(13, "Hestia", 1, 3, BuffAAM11));// 13
		// version 2
		allBabies.add(new Demigod(14, "Aphrodite", 1, 1, BuffERS_10T1));// 0
		allBabies.add(new Demigod(15, "Apollo", 2, 5, SummonChariot));// 1
		allBabies.add(new Demigod(16, "Ares", 5, 2, BuffEBLD_10));// 2
		allBabies.add(new Demigod(17, "Artemis", 3, 2, DamageEFLM2));// 3
		allBabies.add(new Demigod(18, "Athena", 3, 3, DamageEHAF1));// 4
		allBabies.add(new Demigod(19, "Demeter", 2, 3, BuffPUS11));// 5
		allBabies.add(new Demigod(20, "Dionysus", 3, 2, BuffPSM21));// 6
		allBabies.add(new Demigod(21, "Hades", 3, 2, BuffAAM10));// 7
		allBabies.add(new Demigod(22, "Hephaestus", 2, 4, DamagePBM1));// 8
		allBabies.add(new Demigod(23, "Hera", 2, 1, BuffAAM_10));// 9
		allBabies.add(new Demigod(24, "Hemes", 3, 1, DamageAAF2));// 10
		allBabies.add(new Demigod(25, "Poseidon", 1, 2, BuffPLHF20));// 11
		allBabies.add(new Demigod(26, "Zeus", 2, 2, BuffPRM01T1));// 12
		allBabies.add(new Demigod(27, "Hestia", 1, 3, BuffPFLM12));// 13
		// version 3
		allBabies.add(new Demigod(28, "Aphrodite", 1, 1, BuffEFLD1_1));// 0
		allBabies.add(new Demigod(29, "Apollo", 2, 5, BuffAAM01));// 1
		allBabies.add(new Demigod(30, "Ares", 5, 2, DamageEFLF2));// 2
		allBabies.add(new Demigod(31, "Artemis", 3, 2, DamageERS1T1));// 3
		allBabies.add(new Demigod(32, "Athena", 3, 3, SummonArachne));// 4
		allBabies.add(new Demigod(33, "Demeter", 2, 3, SummonCornucopia));// 5
		allBabies.add(new Demigod(34, "Dionysus", 3, 2, BuffEHAM_20));// 6
		allBabies.add(new Demigod(35, "Hades", 3, 2, DamageAAF1));// 7
		allBabies.add(new Demigod(36, "Hephaestus", 2, 4, BuffELHF_31));// 8
		allBabies.add(new Demigod(37, "Hera", 2, 1, DamageAAM1));// 9
		allBabies.add(new Demigod(38, "Hemes", 3, 1, BuffERF_20T1));// 10
		allBabies.add(new Demigod(39, "Poseidon", 1, 2, DamageESLA1));// 11
		allBabies.add(new Demigod(40, "Zeus", 2, 2, DamageARF1T4));// 12
		allBabies.add(new Demigod(41, "Hestia", 1, 3, BuffPBF20));// 13


		// sim.scalePlayerby(300);
		// System.out.println("added all babies");
		// System.out.println("start simulation");

		int size = allBabies.size();
		List<Demigod> EallBabies = new ArrayList<>();
		;
		int[] babies = { Artemis, Dionysus, Poseidon };
		for (int i : babies) {
			EallBabies.add(allBabies.get(i));
			EallBabies.add(allBabies.get(i + 14));
			EallBabies.add(allBabies.get(i + 28));
		}
		// assign builders
		 ATeamBuilder randomAllBabiesBuilder = new RandomTeamBuilder(allBabies);// randomised all babies
		// all combos AllSetsTeamBuilder allBabySets = new AllSetsTeamBuilder(allBabies,
		// 5);
		ATeamBuilder randomEnemyAllBabiesBuilder = new RandomTeamBuilder(EallBabies);// randomize all E Babies
		// specific team in order ConstantTeamBuilder constantEnemyTeamBuilder = new
		// ConstantTeamBuilder(EallBabies);
		 AllSetsTeamBuilder enemyBabySets = new AllSetsTeamBuilder(EallBabies,5);// Daycare
		 AllSetsTeamBuilder allBabySets = new AllSetsTeamBuilder(allBabies,5);// Daycare
		
//		for (String arg : args) {
//			System.out.println();
//		}
		int[] params = new int[3];
//		if (args.length < 3) {
//			System.out.println("Expected 3 arguments: [number of cores] [number of total sessions] [current session index]");
//			return;
//		}
		
		System.out.println("Expected 3 arguments: [number of cores] [number of total sessions] [current session index]");
		Scanner input = new Scanner(System.in);
		for (int i = 0; i < params.length; i++) {
			params[i] = input.nextInt();
		}
//		int cores = Integer.parseInt(args[0]);
//		int sessions = Integer.parseInt(args[1]);
//		int sessionIndex = Integer.parseInt(args[2]);
		int cores = params[0];
		int sessions = params[1];
		int sessionIndex = params[2];
		System.out.printf("Beginning Session %d of %d\n", sessionIndex, sessions);
		BruteForceSession(sessions, sessionIndex, cores, allBabies,EallBabies, 5);
	}

	private static void BruteForce(int jobs, List<Demigod> allBabies, int teamSize) {

		long totalSetCount = AllSetsTeamBuilder.CalculateTotalSets(allBabies.size(), teamSize);

		long startRegion = 0;
		long regionSize = totalSetCount / jobs;

		System.out.printf("Splitting %d tasks across %d threads (%d tasks each)...\n", totalSetCount, jobs, regionSize);

		List<SimulatorThread> threads = new ArrayList<>();

		for (int ii = 0; ii < jobs; ++ii) {
			List<Demigod> allBabiesCopy = new ArrayList<>(allBabies);
			AllSetsTeamBuilder team1 = new AllSetsTeamBuilder(allBabiesCopy, teamSize, startRegion,
					startRegion + regionSize);
			startRegion += regionSize;
			AllSetsTeamBuilder team2 = new AllSetsTeamBuilder(allBabiesCopy, teamSize);

			SimulatorThread thread = new SimulatorThread(allBabiesCopy, team1, team2, teamSize);
			threads.add(thread);
			thread.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Collection<Team> teams = new ArrayList<>();
		for (SimulatorThread sim : threads) {
			teams.addAll(sim.GetTeams());
		}

		try {
			Main.writeIntoExcel(teams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		long uptime = rb.getUptime();
		System.out.println(uptime);

	}

	private static void BruteForceSession(int sessions, int sessionIndex, int jobs, List<Demigod> allBabies,
			int teamSize) {

		String outFileName = String.format("./Team_Data%d.csv", sessionIndex);
		File file = new File(outFileName);

		if (file.canRead()) {
			System.out.printf("File '%s' already exists... Are you sure you want to overwrite it? (y/n)\n",
					outFileName);
			Scanner in = new Scanner(System.in);
			String resp = in.next();

			if (!resp.equalsIgnoreCase("y")) {
				System.out.printf("Did not overwrite file '%s', exiting application...\n", outFileName);
				return;
			} else
				System.out.printf("Overwritting '%s' on completion...\n", outFileName);
		}
		System.out.println("Start: " + LocalTime.now().toString());

		long totalSetCount = AllSetsTeamBuilder.CalculateTotalSets(allBabies.size(), teamSize);

		long sessionSize = totalSetCount / sessions;
		long jobSize = sessionSize / jobs;

		long startRegion = sessionSize * sessionIndex;

		System.out.printf("Starting Session %d/%d:\nSplitting %d tasks across %d threads (%d tasks each)...\n",
				sessionIndex, sessions, sessionSize, jobs, jobSize);

		List<SimulatorThread> threads = new ArrayList<>();

		for (int ii = 0; ii < jobs; ++ii) {
			List<Demigod> allBabiesCopy = new ArrayList<>(allBabies);
			AllSetsTeamBuilder team1 = new AllSetsTeamBuilder(allBabiesCopy, teamSize, startRegion,
					startRegion + jobSize);
			startRegion += jobSize;
			AllSetsTeamBuilder team2 = new AllSetsTeamBuilder(allBabiesCopy, teamSize);

			SimulatorThread thread = new SimulatorThread(allBabiesCopy, team1, team2, teamSize);
			threads.add(thread);
			thread.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Collection<Team> teams = new ArrayList<>();
		for (SimulatorThread sim : threads) {
			teams.addAll(sim.GetTeams());
		}

		try {
			Main.writeIntoExcel(teams, sessionIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		long uptime = rb.getUptime();
		System.out.println("Time:" + (new Timestamp(uptime)).toString());
		System.out.println("EndTIme:" + LocalTime.now());

	}
	
	private static void BruteForceSession(int sessions, int sessionIndex, int jobs, List<Demigod> allBabies,
			List<Demigod> Daycare, int teamSize) {

		String outFileName = String.format("./Team_Data%d.csv", sessionIndex);
		File file = new File(outFileName);

		if (file.canRead()) {
			System.out.printf("File '%s' already exists... Are you sure you want to overwrite it? (y/n)\n",
					outFileName);
			Scanner in = new Scanner(System.in);
			String resp = in.next();

			if (!resp.equalsIgnoreCase("y")) {
				System.out.printf("Did not overwrite file '%s', exiting application...\n", outFileName);
				return;
			} else
				System.out.printf("Overwritting '%s' on completion...\n", outFileName);
		}
		System.out.println("Start: " + LocalTime.now().toString());

		long totalSetCount = AllSetsTeamBuilder.CalculateTotalSets(allBabies.size(), teamSize);

		long sessionSize = totalSetCount / sessions;
		long jobSize = sessionSize / jobs;

		long startRegion = sessionSize * sessionIndex;

		System.out.printf("Starting Session %d/%d:\nSplitting %d tasks across %d threads (%d tasks each)...\n",
				sessionIndex, sessions, sessionSize, jobs, jobSize);

		List<SimulatorThread> threads = new ArrayList<>();

		for (int ii = 0; ii < jobs; ++ii) {
			List<Demigod> allBabiesCopy = new ArrayList<>(allBabies);
			List<Demigod> EallBabiesCopy = new ArrayList<>(Daycare);
			AllSetsTeamBuilder team1 = new AllSetsTeamBuilder(EallBabiesCopy, teamSize, startRegion,
					startRegion + jobSize);
			startRegion += jobSize;
			AllSetsTeamBuilder team2 = new AllSetsTeamBuilder(allBabiesCopy, teamSize);

			SimulatorThread thread = new SimulatorThread(allBabiesCopy, team1, team2, teamSize);
			threads.add(thread);
			thread.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Collection<Team> teams = new ArrayList<>();
		for (SimulatorThread sim : threads) {
			teams.addAll(sim.GetTeams());
		}

		try {
			Main.writeIntoExcel(teams, sessionIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		long uptime = rb.getUptime();
		System.out.println("Time:" + (new Timestamp(uptime)).toString());
		System.out.println("EndTIme:" + LocalTime.now());

	}

}
