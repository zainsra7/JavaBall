package javaball;
public class Team  implements Comparable<Team>{
	private String name; //Name of Team
	private int won; //# of Matches won
	private int lost; //# of Matches lost
	private int drawn; //# of Matches drawn
	private int goalsFor; //Goals scored by the team
	private int goalsAgainst; //Goals scored against the team
	private int matchPoints; //3 points for each win, 1 point for each draw
	private int rank; //Rank of the team 
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWon() {
		return won;
	}

	public void setWon(int won) {
		this.won = won;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	public int getDrawn() {
		return drawn;
	}

	public void setDrawn(int drawn) {
		this.drawn = drawn;
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	Team(String name){
		this.name = name;
	}

	@Override
	public int compareTo(Team o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.name);
	}

	public int getMatchPoints() {
		return matchPoints;
	}

	public void setMatchPoints(int matchPoints) {
		this.matchPoints = matchPoints;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
