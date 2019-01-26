package javaball;
import java.util.Comparator;

public class TeamComparator implements Comparator<Team> {

	@Override
	public int compare(Team a, Team b) {
		/*
		 * - Team A has more MatchPts than B -> Return -1
		 * - Team B has more MatchPts than A -> Return 1
		 * - Team A and B have same MatchPts:
		 *   >Team A and B have same goal difference -> Return 0
		 *   >Team A has more goal difference than B -> Return -1
		 */
		
		if(a.getMatchPoints() > b.getMatchPoints()) return -1;
		
		if(a.getMatchPoints() == b.getMatchPoints()) {
			int goal_diff_A = a.getGoalsFor() - a.getGoalsAgainst();
			int goal_diff_B = b.getGoalsFor() - b.getGoalsAgainst();
			
			if(goal_diff_A == goal_diff_B) return 0;
			else if(goal_diff_A > goal_diff_B) return -1;
		}
		return 1;
	}

}
