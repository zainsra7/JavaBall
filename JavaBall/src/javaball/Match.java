package javaball;

public class Match {
	private Team a;
	private Team b;
	private int goalScoredA; //Goal Scored by Team A, 0-9
	private int goalScoredB; //Goal Scored by Team B, 0-9
	
	Match(Team A,Team B){
		this.a = A;
		this.b = B;
		this.goalScoredA = -1;
		this.goalScoredB = -1;
	}
	
	public Team getA() {
		return a;
	}
	public void setA(Team a) {
		this.a = a;
	}
	public Team getB() {
		return b;
	}
	public void setB(Team b) {
		this.b = b;
	}
	public int getGoalScoredA() {
		return goalScoredA;
	}
	public void setGoalScoredA(int goalScoredA) {
		this.goalScoredA = goalScoredA;
	}
	public int getGoalScoredB() {
		return goalScoredB;
	}
	public void setGoalScoredB(int goalScoredB) {
		this.goalScoredB = goalScoredB;
	}
	
}
