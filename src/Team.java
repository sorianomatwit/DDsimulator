import java.util.ArrayList;

public class Team {
	///NOTE: NOT YET IOMPLEMENTED DO NOT USE
	public ArrayList<Demigod> myTeam;
	public double amtofGames;
	public double wins;
	public double lose;
	public String name;
	public Team(String name, ArrayList<Demigod> A) {
		myTeam = A;
		this.name = name;
	}
	
	public double winRate(){
		return wins/this.amtofGames;
	}
	public double loseRate(){
		return lose/this.amtofGames;
	}
	
	@Override
	public String toString() {
		double draws = this.amtofGames - wins - lose;
		return String.format("%s: Wr:%.4f | Lr %.4f | Dr %.4f",name, this.winRate(),this.loseRate(),draws/this.amtofGames);
	}
}
