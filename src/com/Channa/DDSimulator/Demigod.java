package com.Channa.DDSimulator;

import com.Channa.DDSimulator.Abilities.Ability;

public class Demigod {
	public final int ID;
	private String name;
	public int atk;
	public int hp;
	private Ability ability;
	public double wins = 0;
	public double lose = 0;
	public double amtGames = 0;
	public boolean isDead = false;
	public double deathCount = 0;
	public boolean isSummon = false;
	public Demigod(int ID, String name, int atk, int hp, Ability ability) {
		this.ID = ID;
		this.name = name;
		this.atk = atk;
		this.hp = hp;
		this.ability = ability;
	}
	public Demigod(int ID, String name, int atk, int hp) {
		this.ID = ID;
		this.name = name;
		this.atk = atk;
		this.hp = hp;
		isSummon = true;
	}
	
	public Demigod(Demigod other) {
		this.ID = other.ID;
		this.name = other.name;
		this.atk = other.atk;
		this.hp = other.hp;
		if(other.ability != null) {
			this.ability = other.ability;
		} else isSummon = true;
	}

	public Ability getAbility() {
		return ability;
	}
	
	public void Won() {
		wins++;
	}

	public void Lost() {
		lose++;
	}

	public void gamePlayed() {
		amtGames++;
	}
	
	public void died() {
		isDead = true;
		
		//System.out.printf("%s Died%n",name);
	}
	public String getName() {
		return name;
	}
	
	public void addStats(Demigod baby){
		wins += baby.wins;
		lose += baby.lose;
		amtGames += baby.amtGames;
		deathCount += deathCount;
	}
	public String stats() {
		if (amtGames > 0)
			return String.format("%s: WR:%.4f | LR:%.4f | DR:%.4f | DeathR:%.4f | GP:%.0f",
				name.substring(0, 3),
				getWinRate(),
				getLoseRate(),
				getDrawRate(),
				deathCount/amtGames,
				amtGames);

		return String.format("%s: WR:NA     | LR:NA     | DR:NA     | DeathR:NA     | GP:0",
			name.substring(0, 3));
	}
	public double getWinRate() {
		return wins/amtGames;
	}
	public double getLoseRate() {
		return lose/amtGames;
	}
	public double getDeathRate() {
		return deathCount/amtGames;
	}
	public double getDrawRate() {
		double draws = (amtGames - wins - lose);
		return draws/amtGames;
	}
	public double amtOfGames() {
		return amtGames;
	}
	public double deathRate() {
		return deathCount/(double)amtGames;
	}
	@Override
	public String toString() {
		//String s = String.format("%s : W:%.2f L:%.2f",name, wins/amtGames, lose/amtGames);
		String s = String.format("%s:%s", name.substring(0,4), ability.GetName());
		return s;
	}
	
	public boolean equals(Demigod Baby) {
		return Baby.getName().equals(this.name);
	}
	
	public int hashCode() {
		return name.hashCode();
	}
	
}
