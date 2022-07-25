import AbilityEnums.AbilityTrigger;

public class Demigod {
	private String name;
	public int atk;
	public int hp;
	private Ability ability;
	public double wins;
	public double lose;
	public int amtGames;
	public boolean isDead = false;
	public double deathCount;
	public boolean isSummon = false;
	public Demigod(String name, int atk, int hp, Ability ability) {
		this.name = name;
		this.atk = atk;
		this.hp = hp;
		this.ability = ability;
	}
	public Demigod(String name, int atk, int hp) {
		this.name = name;
		this.atk = atk;
		this.hp = hp;
		isSummon = true;
	}
	
	public Demigod(Demigod other) {
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
		double draws = (amtGames - wins - lose);
		return String.format("%s: WR:%.4f | LR:%.4f | DR:%.4f | DeathR:%.4f",
				name.substring(0, 3), wins/amtGames, lose/amtGames, draws/amtGames, deathCount/amtGames);
	}
	public double getWinRate() {
		return wins/amtGames;
	}
	public double getLoseRate() {
		return lose/amtGames;
	}
	public double getDrawRate() {
		double draws = (amtGames - wins - lose);
		return draws/amtGames;
	}
	public int amtOfGames() {
		return amtGames;
	}
	public double deathRate() {
		return deathCount/amtGames;
	}
	@Override
	public String toString() {
		//String s = String.format("%s : W:%.2f L:%.2f",name, wins/amtGames, lose/amtGames);
		String s = String.format("%s %d/%d", name.substring(0,3),atk,hp);
		return s;
	}
	
	
	public boolean equals(Demigod Baby) {
		return Baby.getName().equals(this.name);
	}
	
	
}
