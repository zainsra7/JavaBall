package javaball;
/**
 * 
 * Model Class - Tournament, Containing all the information about Tournament including matches, teams, methods and to generate Table.
 */

import java.io.*;
import java.util.*;

public class Tournament {
	
	//Fields
	private ArrayList<Team> teams; //# of Teams
	private ArrayList<Match> matches; //# of Matches

	//Constructor
	Tournament(){
		teams = new ArrayList<>();
		matches = new ArrayList<>();
	}
	
	//Getters
	public ArrayList<Team> getTeams(){
		return teams;
	}
	public ArrayList<Match> getMatches() {
		return matches;
	}
	
	//Get Team Names as String and String[]
	public String getTeamList() {
		
		String result = "Teams >>> | ";
		
		for(Team t : teams) {
			result += t.getName() + " | ";
		}
		return result;
	}
	public String[] getTeamAsArray() {
		String [] result = new String[teams.size()];
		int index = 0;
		for(Team t : teams) {
			result[index++] = t.getName();
		}
		return result;
	}
	
	//Get Matches List as String and List
	public String getMatchesList() {
		String result = "\t\t Matches-List \n\n";
		
		for (Match m : matches) {
			
			if(m.getGoalScoredA() == -1 || m.getGoalScoredB() == -1) {
				result += m.getA().getName() + " v " + m.getB().getName() + " *** no results yet ***\n";
			}
			else {
				result += m.getA().getName() + " " + m.getGoalScoredA() +" " + m.getB().getName() + " " + m.getGoalScoredB()+"\n";
			}
			
		}
		return result;
	}
	public List<String> getUnprocessedMatchList(){
		List<String> result = new ArrayList<>();
		
		for(Match m : matches) {
			if(m.getGoalScoredA() == -1 || m.getGoalScoredB() == -1) {
				String temp = m.getA().getName() + "-" + m.getB().getName();
				result.add(temp);
			}
		}
		
		return result;
	}
	
	//Reading TeamsIn and ResultsIn Files
	public String read_TeamsIn(){
		
		String result_string = "\t\t Matches-List\n\n"; //Matches-List to display in the textArea
		/*
		 * Reading from TeamsIn.txt and populating teams list [Assuming that file has correct input]
		 * Sorting the teams list in ascending order
		 * Create matches (initialization), i.e a team has to play against every other team exactly once.
		 */
		
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("TeamsIn.txt"));
			String line = reader.readLine();
			while(line !=null) {
				Team temp = new Team(line);
				teams.add(temp);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "File not Found!";
		}
		
		//Sorting Teams based on their Names
		Collections.sort(teams);
		
		//Creating Matches
		for(int i = 0; i < teams.size(); i++) {
			for(int j = i + 1; j < teams.size(); j++) {
				Match temp = new Match(teams.get(i),teams.get(j));
				matches.add(temp);
			}
		}
		
		for(Match m : matches) {
			result_string += m.getA().getName() + " -vs- " + m.getB().getName() + " *** no results yet ***\n";
		}
		return result_string;
	}
	public String read_ResultsIn(){
		
		String result_string = "\t\t Matches-List \n\n"; //String to display Matches-List after reading ResultsIn
		/*
		 * Reading ResultsIn.txt and populating matches list
		 * (Assuming that the ResultsIn have valid data)
		 */	
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("ResultsIn.txt"));
			String line = reader.readLine();
			while(line !=null) {
				//Splitting each line on whitespace, so we have
				//TeamA -> index(0)
				//TeamA_Goals -> index(1)
				//TeamB -> index(2)
				//TeamB_Goals -> index(3)
				String[] match_result = line.split(" ");
				
				//Populating Match-List
				for (Match m : matches) {
					if(m.getA().getName().equals(match_result[0]) &&
					m.getB().getName().equals(match_result[2])) {
						//Ford vs Park
						
						//Setting Goals for Team A and B 
						int goalScoredBy_A = Integer.valueOf(match_result[1]);
						int goalScoredBy_B = Integer.valueOf(match_result[3]);
						m.setGoalScoredA(goalScoredBy_A);
						m.setGoalScoredB(goalScoredBy_B);
						
						m.getA().setGoalsFor(m.getA().getGoalsFor() + goalScoredBy_A);
						m.getA().setGoalsAgainst(m.getA().getGoalsAgainst() + goalScoredBy_B);
						
						m.getB().setGoalsFor(m.getB().getGoalsFor() + goalScoredBy_B);
						m.getB().setGoalsAgainst(m.getB().getGoalsAgainst() + goalScoredBy_A);
						
						//Setting Win/Loss/Draw/MatchPoints for Team A and B
						if(goalScoredBy_A > goalScoredBy_B) {
							m.getA().setWon(m.getA().getWon() + 1);
							m.getB().setLost(m.getB().getLost() + 1);
							
							m.getA().setMatchPoints(m.getA().getMatchPoints() + 3);
						}else if(goalScoredBy_A == goalScoredBy_B) {
							m.getA().setDrawn(m.getA().getDrawn() + 1);
							m.getB().setDrawn(m.getB().getDrawn() + 1);
							

							m.getA().setMatchPoints(m.getA().getMatchPoints() + 1);
							m.getB().setMatchPoints(m.getB().getMatchPoints() + 1);
						}else {
							m.getB().setWon(m.getB().getWon() + 1);
							m.getA().setLost(m.getA().getLost() + 1);
							
							m.getB().setMatchPoints(m.getB().getMatchPoints() + 3);
						}			
					}//end of if
					else if(m.getA().getName().equals(match_result[2]) &&
							m.getB().getName().equals(match_result[0])){
						//Park vs Ford

						//Setting Goals for Team A and B 
						int goalScoredBy_A = Integer.valueOf(match_result[3]);
						int goalScoredBy_B = Integer.valueOf(match_result[1]);
						m.setGoalScoredA(goalScoredBy_A);
						m.setGoalScoredB(goalScoredBy_B);
						
						m.getA().setGoalsFor(m.getA().getGoalsFor() + goalScoredBy_A);
						m.getA().setGoalsAgainst(m.getA().getGoalsAgainst() + goalScoredBy_B);
						
						m.getB().setGoalsFor(m.getB().getGoalsFor() + goalScoredBy_B);
						m.getB().setGoalsAgainst(m.getB().getGoalsAgainst() + goalScoredBy_A);
						
						//Setting Win/Loss/Draw for Team A and B
						if(goalScoredBy_A > goalScoredBy_B) {
							m.getA().setWon(m.getA().getWon() + 1);
							m.getB().setLost(m.getB().getLost() + 1);
							
							m.getA().setMatchPoints(m.getA().getMatchPoints() + 3);
						}else if(goalScoredBy_A == goalScoredBy_B) {
							m.getA().setDrawn(m.getA().getDrawn() + 1);
							m.getB().setDrawn(m.getB().getDrawn() + 1);

							m.getA().setMatchPoints(m.getA().getMatchPoints() + 1);
							m.getB().setMatchPoints(m.getB().getMatchPoints() + 1);
						}else {
							m.getB().setWon(m.getB().getWon() + 1);
							m.getA().setLost(m.getA().getLost() + 1);
							
							m.getB().setMatchPoints(m.getB().getMatchPoints() + 3);
						}								
					}//end of else-if
				}//end of for
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "File not Found!";
		}
		
		
		for (Match m : matches) {
			
			if(m.getGoalScoredA() == -1 || m.getGoalScoredB() == -1) {
				result_string += m.getA().getName() + " v " + m.getB().getName() + " *** no results yet ***\n";
			}
			else {
				result_string += m.getA().getName() + " " + m.getGoalScoredA() +" " + m.getB().getName() + " " + m.getGoalScoredB()+"\n";
			}
			
		}
		return result_string;
	}
	
	//Check for unprocessed matches
	//To Find if there is any unprocessed match left in the Match-List
	public boolean isAnyMatch_Left() {
		for(Match m : matches) {
			if(m.getGoalScoredA() == -1 || m.getGoalScoredB() == -1) return true;
		}
		return false;
	}
	
	//To Withdraw a Team from Tournament
	public String withdraw(String team_to_remove) {
		
		boolean isTeamRemoved = false;
		//Remove from Match-List
		
		List<Match> to_remove = new ArrayList<>(); //List of Matches to Remove
		
		for(Match m : matches) {	
			if(m.getA().getName().equals(team_to_remove) || m.getB().getName().equals(team_to_remove)){
				to_remove.add(m);
				isTeamRemoved = true;
			}		
		}
		
		for(Match tm : to_remove) {
			matches.remove(tm);
		}
		
		//Remove from Team-List
				for (int i = 0; i < teams.size(); i++) {
					if(teams.get(i).getName().equals(team_to_remove)) {
						teams.remove(i);
						isTeamRemoved = true;
					}
				}
				if(isTeamRemoved == true) {
					return team_to_remove + " Removed Successfully!";
				}
		return "Can't remove "+team_to_remove+"!";
	}
	
	//To Generate Ranked Table
	public String[][] generate_RankedTable() {
		
		String resulted_table[][] = new String[teams.size()][10]; //Rows x Cols for the Table
		
		//Sorting Team-List based on their MatchPoints + Goal Difference
		Collections.sort(teams,new TeamComparator());
		
		//Setting Ranks for each team
		int rank = 1;
		teams.get(0).setRank(rank); //First Team is always ranked 1
		
		for(int i = 1; i < teams.size(); i++) {
			int pts_a = teams.get(i-1).getMatchPoints();
			int gdif_a = teams.get(i-1).getGoalsFor() - teams.get(i-1).getGoalsAgainst();
			int gdif_b = teams.get(i).getGoalsFor() - teams.get(i).getGoalsAgainst();
			
			//If current and previous team have same match points and goal difference, then give them same rank
			if(teams.get(i).getMatchPoints() == pts_a && (gdif_a == gdif_b)) {
				teams.get(i).setRank(rank);
			}
			else {
				teams.get(i).setRank(++rank);
			}
		}//end of for

		//Filling out the table
		
		for(int i = 0; i < teams.size(); i++) {
				resulted_table[i][0] = teams.get(i).getName();
				resulted_table[i][1] = Integer.toString(teams.get(i).getRank());
				resulted_table[i][2] = Integer.toString(teams.get(i).getWon());
				resulted_table[i][3] = Integer.toString(teams.get(i).getDrawn());
				resulted_table[i][4] = Integer.toString(teams.get(i).getLost());
				resulted_table[i][5] = Integer.toString(teams.get(i).getGoalsFor());
				resulted_table[i][6] = Integer.toString(teams.get(i).getGoalsAgainst());
				resulted_table[i][7] = Integer.toString(teams.get(i).getMatchPoints());
				resulted_table[i][8] = Integer.toString(teams.get(i).getGoalsFor() - teams.get(i).getGoalsAgainst());
				
				if(teams.get(i).getRank() == 1) resulted_table[i][9] = "Gold";
				else if(teams.get(i).getRank() == 2) resulted_table[i][9] = "Silver";
				else if(teams.get(i).getRank() == 3) resulted_table[i][9] = "Bronze";
				else resulted_table[i][9] = "";
		}
		return resulted_table;
	}
	
	//To Add Results manually for a Match
	public String addResults(String team_A,String goal_A,String team_B,String goal_B) {
		
		int goal_scoredBy_A = Integer.parseInt(goal_A);
		int goal_scoredBy_B = Integer.parseInt(goal_B);
		
		for (Match m : matches) {
			if(m.getA().getName().equals(team_A) && m.getB().getName().equals(team_B)) {
				
				m.setGoalScoredA(goal_scoredBy_A);
				m.setGoalScoredB(goal_scoredBy_B);
				
				m.getA().setGoalsFor(m.getA().getGoalsFor() + goal_scoredBy_A);
				m.getA().setGoalsAgainst(m.getA().getGoalsAgainst() + goal_scoredBy_B);
				
				m.getB().setGoalsFor(m.getB().getGoalsFor() + goal_scoredBy_B);
				m.getB().setGoalsAgainst(m.getB().getGoalsAgainst() + goal_scoredBy_A);
				
				//Setting Win/Loss/Draw/MatchPoints for Team A and B
				if(goal_scoredBy_A > goal_scoredBy_B) {
					m.getA().setWon(m.getA().getWon() + 1);
					m.getB().setLost(m.getB().getLost() + 1);
					
					m.getA().setMatchPoints(m.getA().getMatchPoints() + 3);
				}else if(goal_scoredBy_A == goal_scoredBy_B) {
					m.getA().setDrawn(m.getA().getDrawn() + 1);
					m.getB().setDrawn(m.getB().getDrawn() + 1);
					

					m.getA().setMatchPoints(m.getA().getMatchPoints() + 1);
					m.getB().setMatchPoints(m.getB().getMatchPoints() + 1);
				}else {
					m.getB().setWon(m.getB().getWon() + 1);
					m.getA().setLost(m.getA().getLost() + 1);
					
					m.getB().setMatchPoints(m.getB().getMatchPoints() + 3);
				}			
				return "Result Added Successfully!";
			}//end of if
		}
		
		return "Couldn't Add the result!";
	}
	
	//To Write out Results onto an output File
	public String resultsOut() {
		//Write Matches List or Ranked Table in a "ResultsOut.txt" File
		 try {
			FileWriter fw=new FileWriter("ResultsOut.txt");
			if(isAnyMatch_Left()) {
				//There is unprocessed Match, So we will simply write the Matches-List onto the file
				String result_string = getTeamList();
				fw.write(result_string);
				fw.write("\r\n\r\n");
				fw.write("\t\t ------------Matches-List-------------");
				fw.write("\r\n\r\n");
				
				for (Match m : matches) {
					
					if(m.getGoalScoredA() == -1 || m.getGoalScoredB() == -1) {
						fw.write(m.getA().getName() + " v " + m.getB().getName() + " *** no results yet ***");
						fw.write("\r\n");
					}
					else {
						fw.write(m.getA().getName() + " " + m.getGoalScoredA() +" " + m.getB().getName() + " " + m.getGoalScoredB());
						fw.write("\r\n");
					}	
				}
			}else {
				String[][] ranked_table = generate_RankedTable();
				String result = "Team | Rank | Won | Drawn | Lost | GFor | GAgst | MtPts | GDiff | Medal";
				fw.write(result);
				fw.write("\r\n\r\n");
				for(int i=0; i< teams.size(); i++) {
					fw.write(ranked_table[i][0]+" | ");
					for(int j=1; j<10;j++) {
						fw.write(ranked_table[i][j]+" | ");
					}
					fw.write("\r\n--------------------------------\r\n");
				}
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "Can't Write the File!";
		}
		 return "See ResultsOut.txt!";
	}
}
